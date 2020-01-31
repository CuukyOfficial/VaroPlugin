package de.cuuky.varo.entity.player;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.Main;
import de.cuuky.varo.alert.Alert;
import de.cuuky.varo.alert.AlertType;
import de.cuuky.varo.bot.BotLauncher;
import de.cuuky.varo.bot.discord.VaroDiscordBot;
import de.cuuky.varo.bot.discord.register.BotRegister;
import de.cuuky.varo.configuration.config.ConfigEntry;
import de.cuuky.varo.configuration.messages.ConfigMessages;
import de.cuuky.varo.entity.VaroEntity;
import de.cuuky.varo.entity.player.event.BukkitEvent;
import de.cuuky.varo.entity.player.event.BukkitEventType;
import de.cuuky.varo.entity.player.stats.Stats;
import de.cuuky.varo.entity.player.stats.stat.PlayerState;
import de.cuuky.varo.entity.player.stats.stat.Rank;
import de.cuuky.varo.entity.player.stats.stat.offlinevillager.OfflineVillager;
import de.cuuky.varo.entity.team.VaroTeam;
import de.cuuky.varo.event.VaroEvent;
import de.cuuky.varo.game.Game;
import de.cuuky.varo.game.lobby.LobbyItem;
import de.cuuky.varo.game.state.GameState;
import de.cuuky.varo.logger.logger.EventLogger;
import de.cuuky.varo.logger.logger.EventLogger.LogType;
import de.cuuky.varo.scoreboard.ScoreboardHandler;
import de.cuuky.varo.scoreboard.nametag.Nametag;
import de.cuuky.varo.serialize.identifier.VaroSerializeField;
import de.cuuky.varo.utils.JavaUtils;
import de.cuuky.varo.vanish.Vanish;
import de.cuuky.varo.version.BukkitVersion;
import de.cuuky.varo.version.VersionUtils;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;

public class VaroPlayer extends VaroEntity {

	private static ArrayList<VaroPlayer> varoplayer;

	static {
		varoplayer = new ArrayList<>();
	}

	@VaroSerializeField(path = "id")
	private int id;
	
	@VaroSerializeField(path = "name")
	private String name;
	
	@VaroSerializeField(path = "uuid")
	private String uuid;
	
	@VaroSerializeField(path = "adminIgnore")
	private boolean adminIgnore;

	@VaroSerializeField(path = "villager")
	private OfflineVillager villager;
	
	@VaroSerializeField(path = "rank")
	private Rank rank;
	
	@VaroSerializeField(path = "stats")
	private Stats stats;

	private Nametag nametag;
	private NetworkMananager networkManager;
	private String tabName;
	private VaroTeam team;
	private Player player;
	private boolean alreadyHadMassProtectionTime, inMassProtectionTime, massRecordingKick;

	public VaroPlayer() {
		varoplayer.add(this);
	}

	public VaroPlayer(Player player) {
		this.name = player.getName();
		this.uuid = player.getUniqueId().toString();
		this.player = player;
		this.id = generateId();
		this.adminIgnore = false;

		this.stats = new Stats(this);
	}

	public VaroPlayer(String playerName, String uuid) {
		this.name = playerName;
		this.uuid = uuid;

		this.adminIgnore = false;
		this.id = generateId();

		varoplayer.add(this);
		this.stats = new Stats(this);
		stats.loadDefaults();
	}

	private int generateId() {
		int id = JavaUtils.randomInt(1000, 9999999);
		while(getPlayer(id) != null)
			generateId();

		return id;
	}

	private void updateDiscordTeam(VaroTeam oldTeam) {
		VaroDiscordBot db = BotLauncher.getDiscordBot();
		if(db == null || !db.isEnabled())
			return;

		Member member = BotRegister.getBotRegisterByPlayerName(name).getMember();
		if(oldTeam != null) {
			if(db.getController().getGuild().getRolesByName("#" + oldTeam.getName(), true).size() > 0) {
				Role role = db.getController().getGuild().getRolesByName("#" + oldTeam.getName(), true).get(0);
				db.getController().removeSingleRoleFromMember(member, role).complete();
			}
		}

		if(this.team != null) {
			Role role = db.getController().getGuild().getRolesByName("#" + team.getName(), true).size() > 0 ? db.getController().getGuild().getRolesByName("#" + team.getName(), true).get(0) : null;
			if(role == null)
				role = db.getController().createCopyOfRole(db.getController().getGuild().getPublicRole()).setHoisted(true).setName("#" + team.getName()).complete();

			db.getController().addRolesToMember(member, role).complete();
		}

	}

	/**
	 * @return Returns if a player is nearby
	 */
	public boolean canBeKicked(int noKickDistance) {
		if(noKickDistance < 1)
			return true;

		for(Entity entity : player.getNearbyEntities(noKickDistance, noKickDistance, noKickDistance)) {
			if(!(entity instanceof Player))
				continue;

			VaroPlayer vp = getPlayer((Player) entity);
			if(vp.equals(this))
				continue;

			if(vp.getTeam() != null)
				if(vp.getTeam().equals(team))
					continue;

			if(vp.getStats().isSpectator() || vp.isAdminIgnore())
				continue;

			return false;
		}

		return true;
	}

	public void cleanUpPlayer() {
		player.setHealth(20);
		player.setFoodLevel(20);
		player.getInventory().clear();
		player.getInventory().setArmorContents(new ItemStack[] {});
		player.setExp(0);
		player.setLevel(0);
	}

	public void delete() {
		if(team != null)
			team.removeMember(this);

		if(rank != null)
			rank.remove();

		if(isOnline())
			player.kickPlayer(ConfigMessages.JOIN_KICK_NOT_USER_OF_PROJECT.getValue(this));

		if(villager != null)
			villager.remove();

		stats.remove();
		varoplayer.remove(this);
		ScoreboardHandler.getInstance().updateTopScores();
	}
	
	@Override
	public void onDeserializeEnd() {
		this.player = Bukkit.getPlayer(getRealUUID()) != null ? Bukkit.getPlayer(getRealUUID()) : null;
		if(isOnline()) {
			update();

			if(getStats().isSpectator() || isAdminIgnore())
				setSpectacting();

			setNormalAttackSpeed();

			if(Game.getInstance().getGameState() == GameState.LOBBY)
				LobbyItem.giveItems(player);
		} else if(isAdminIgnore())
			adminIgnore = false;

		this.stats.setOwner(this);
	}

	public void onEvent(BukkitEventType type) {
		new BukkitEvent(this, type);
	}

	@Override
	public void onSerializeStart() {}

	public void register() {
		if(this.stats == null)
			this.stats = new Stats(this);

		stats.loadDefaults();
		varoplayer.add(this);
	}

	public String getPrefix() {
		String pr = "";
		if(team != null)
			pr = team.getDisplay() + " ";

		if(rank != null)
			pr = rank.getDisplay() + (pr.isEmpty() ? " " : " §8| ") + pr;
		
		return pr;
	}
	
	public void setSpectacting() {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				new Vanish(player.getPlayer());
				player.getPlayer().setGameMode(GameMode.ADVENTURE);
				player.getPlayer().setAllowFlight(true);
				player.getPlayer().setFlying(true);
				player.getPlayer().setHealth(20);
				player.getPlayer().setFoodLevel(20);

				if(!adminIgnore) {
					player.getInventory().clear();
					player.getInventory().setArmorContents(new ItemStack[] {});
				}
			}
		}, 1);
	}

	public void update() {
		if(!isOnline())
			return;

		if(ConfigEntry.NAMETAGS.getValueAsBoolean()) {
			if(nametag == null)
				nametag = new Nametag(player.getUniqueId(), player);
			else
				nametag.refresh();
		}

		if(VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_7)) {
			if(ConfigEntry.TABLIST.getValueAsBoolean()) {
				getNetworkManager().sendTablist();
			}

			String listname = "";
			if(getTeam() != null) {
				if(getRank() == null) {
					listname = ConfigMessages.TABLIST_PLAYER_WITH_TEAM.getValue(this);
				} else {
					listname = ConfigMessages.TABLIST_PLAYER_WITH_TEAM_RANK.getValue(this);
				}
			} else {
				if(getRank() == null) {
					listname = ConfigMessages.TABLIST_PLAYER_WITHOUT_TEAM.getValue(this);
				} else {
					listname = ConfigMessages.TABLIST_PLAYER_WITHOUT_TEAM_RANK.getValue(this);
				}
			}

			if(this.tabName == null || !this.tabName.equals(listname))
				player.setPlayerListName(this.tabName = listname);
		}
	}
	
	public boolean getalreadyHadMassProtectionTime() {
		return alreadyHadMassProtectionTime;
	}

	public int getId() {
		return id;
	}

	public boolean getinMassProtectionTime() {
		return inMassProtectionTime;
	}

	public String getName() {
		return name;
	}

	public Nametag getNametag() {
		return nametag;
	}

	public NetworkMananager getNetworkManager() {
		return networkManager == null ? new NetworkMananager(player) : networkManager;
	}

	public Player getPlayer() {
		return player;
	}

	public Rank getRank() {
		return rank;
	}

	public UUID getRealUUID() {
		return UUID.fromString(uuid);
	}

	public Stats getStats() {
		return stats;
	}

	public VaroTeam getTeam() {
		return team;
	}

	public String getUuid() {
		return uuid;
	}

	public OfflineVillager getVillager() {
		return villager;
	}

	public boolean isAdminIgnore() {
		return adminIgnore;
	}

	public boolean isInProtection() {
		if(VaroEvent.getMassRecEvent().isEnabled()) {
			return inMassProtectionTime;
		} else {
			return ConfigEntry.PLAY_TIME.isIntActivated() && stats.getCountdown() >= (ConfigEntry.PLAY_TIME.getValueAsInt() * 60) - ConfigEntry.JOIN_PROTECTIONTIME.getValueAsInt();
		}
	}

	public boolean isMassRecordingKick() {
		return massRecordingKick;
	}

	/**
	 * @return Returns if the Player is online
	 */
	public boolean isOnline() {
		return player != null;
	}

	public boolean isRegistered() {
		return varoplayer.contains(this);
	}

	public void sendMessage(String message) {
		player.sendMessage(message);
	}

	public void setAdminIgnore(boolean adminIgnore) {
		this.adminIgnore = adminIgnore;
	}

	public void setalreadyHadMassProtectionTime(boolean alreadyHadMassProtectionTime) {
		this.alreadyHadMassProtectionTime = alreadyHadMassProtectionTime;
	}

	public void setinMassProtectionTime(boolean inMassProtectionTime) {
		this.inMassProtectionTime = inMassProtectionTime;
	}

	public void setMassRecordingKick(boolean massRecordingKick) {
		this.massRecordingKick = massRecordingKick;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNametag(Nametag nametag) {
		this.nametag = nametag;
	}

	public void setNetworkManager(NetworkMananager networkManager) {
		this.networkManager = networkManager;
	}

	public void setNormalAttackSpeed() {
		getNetworkManager().setAttributeSpeed(!ConfigEntry.REMOVE_HIT_COOLDOWN.getValueAsBoolean() ? 4D : 100D);
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void setRank(Rank rank) {
		this.rank = rank;
		update();
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public void setVillager(OfflineVillager villager) {
		this.villager = villager;
	}
	
	public void setTeam(VaroTeam team) {
		VaroTeam oldTeam = this.team;
		this.team = team;

		if(!Main.isBootedUp())
			return;

		try {
			if(ConfigEntry.DISCORDBOT_SET_TEAM_AS_GROUP.getValueAsBoolean())
				updateDiscordTeam(oldTeam);
		} catch(Exception e) {
			e.printStackTrace();
		}

		update();
		ScoreboardHandler.getInstance().updateTopScores();
	}

	/**
	 * @return Returns all alive Players regardless if they are online
	 */
	public static ArrayList<VaroPlayer> getAlivePlayer() {
		ArrayList<VaroPlayer> alive = new ArrayList<>();
		for(VaroPlayer vp : varoplayer) {
			if(!vp.getStats().isAlive())
				continue;

			alive.add(vp);
		}

		return alive;
	}

	public static ArrayList<VaroPlayer> getDeadPlayer() {
		ArrayList<VaroPlayer> dead = new ArrayList<>();
		for(VaroPlayer vp : varoplayer) {
			if(vp.getStats().getState() != PlayerState.DEAD)
				continue;

			dead.add(vp);
		}

		return dead;
	}

	public static ArrayList<VaroPlayer> getOnlineAndAlivePlayer() {
		ArrayList<VaroPlayer> online = new ArrayList<>();
		for(VaroPlayer vp : varoplayer) {
			if(!vp.isOnline() || !vp.getStats().isAlive())
				continue;

			online.add(vp);
		}

		return online;
	}

	/**
	 * @return Returns all online VaroPlayers regardless if they are alive
	 */
	public static ArrayList<VaroPlayer> getOnlinePlayer() {
		ArrayList<VaroPlayer> online = new ArrayList<>();
		for(VaroPlayer vp : varoplayer) {
			if(!vp.isOnline())
				continue;

			online.add(vp);
		}

		return online;
	}

	public static VaroPlayer getPlayer(int id) {
		for(VaroPlayer vp : varoplayer) {
			if(vp.getId() != id)
				continue;

			return vp;
		}

		return null;
	}

	/**
	 * @return Returns the varoplayer and sets the name right if the player
	 *         changed it before
	 */
	public static VaroPlayer getPlayer(Player player) {
		for(VaroPlayer vp : varoplayer) {
			if(vp.getUuid() != null)
				if(!vp.getUuid().equals(player.getUniqueId().toString()))
					continue;

			if(vp.getUuid() == null && player.getName().equalsIgnoreCase(vp.getName()))
				vp.setUuid(player.getUniqueId().toString());
			else if(vp.getUuid() == null)
				continue;

			if(!vp.getName().equalsIgnoreCase(player.getName())) {
				EventLogger.getInstance().println(LogType.ALERT, ConfigMessages.ALERT_SWITCHED_NAME.getValue(vp).replace("%newName%", player.getName()));
				Bukkit.broadcastMessage("§c" + vp.getName() + " §7hat seinen Namen gewechselt und ist nun unter §c" + player.getName() + " §7bekannt!");
				new Alert(AlertType.NAME_SWITCH, vp.getName() + " §7hat seinen Namen gewechselt und ist nun unter §c" + player.getName() + " §7bekannt!");
				vp.setName(player.getName());
			}

			return vp;
		}

		return null;
	}

	public static VaroPlayer getPlayer(String name) {
		for(VaroPlayer vp : varoplayer) {
			if(!vp.getName().equalsIgnoreCase(name))
				continue;

			return vp;
		}

		return null;
	}

	public static ArrayList<VaroPlayer> getSpectator() {
		ArrayList<VaroPlayer> spectator = new ArrayList<>();
		for(VaroPlayer vp : varoplayer) {
			if(!vp.getStats().isSpectator())
				continue;

			spectator.add(vp);
		}

		return spectator;
	}

	public static ArrayList<VaroPlayer> getVaroPlayer() {
		return varoplayer;
	}
}