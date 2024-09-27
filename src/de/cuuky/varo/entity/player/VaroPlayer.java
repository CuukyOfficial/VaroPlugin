package de.cuuky.varo.entity.player;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.cryptomorin.xseries.XSound;

import de.cuuky.cfw.configuration.language.broadcast.MessageHolder;
import de.cuuky.cfw.configuration.language.languages.LoadableMessage;
import de.cuuky.cfw.player.CustomLanguagePlayer;
import de.cuuky.cfw.player.CustomPlayer;
import de.cuuky.cfw.player.clientadapter.BoardUpdateHandler;
import de.cuuky.varo.Main;
import de.cuuky.varo.alert.Alert;
import de.cuuky.varo.alert.AlertType;
import de.cuuky.varo.bot.discord.VaroDiscordBot;
import de.cuuky.varo.bot.discord.register.BotRegister;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.event.BukkitEvent;
import de.cuuky.varo.entity.player.event.BukkitEventType;
import de.cuuky.varo.entity.player.stats.Stats;
import de.cuuky.varo.entity.player.stats.stat.PlayerState;
import de.cuuky.varo.entity.player.stats.stat.Rank;
import de.cuuky.varo.entity.player.stats.stat.offlinevillager.OfflineVillager;
import de.cuuky.varo.entity.team.VaroTeam;
import de.cuuky.varo.event.VaroEvent;
import de.cuuky.varo.event.VaroEventType;
import de.cuuky.varo.game.lobby.LobbyItem;
import de.cuuky.varo.gui.settings.VaroMenuColor;
import de.cuuky.varo.listener.helper.ChatMessage;
import de.cuuky.varo.logger.logger.EventLogger.LogType;
import de.cuuky.varo.serialize.identifier.VaroSerializeField;
import de.cuuky.varo.serialize.identifier.VaroSerializeable;
import de.cuuky.varo.vanish.Vanish;
import de.varoplugin.cfw.player.PlayerVersionAdapter;
import de.varoplugin.cfw.player.SafeTeleport;
import de.varoplugin.cfw.player.hud.AnimatedActionbar;
import de.varoplugin.cfw.player.hud.AnimatedScoreboard;
import de.varoplugin.cfw.player.hud.AnimatedTablist;
import de.varoplugin.cfw.player.hud.ScoreboardInstance;
import de.varoplugin.cfw.utils.JavaUtils;
import de.varoplugin.cfw.version.ServerVersion;
import de.varoplugin.cfw.version.VersionUtils;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.UserSnowflake;

public class VaroPlayer extends CustomLanguagePlayer implements CustomPlayer, VaroSerializeable {

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

	@VaroSerializeField(path = "locale")
	private String locale;

	@VaroSerializeField(path = "adminIgnore")
	private boolean adminIgnore;

	@VaroSerializeField(path = "villager")
	private OfflineVillager villager;

	@VaroSerializeField(path = "rank")
	private Rank rank;

	@VaroSerializeField(path = "stats")
	private Stats stats;

	@VaroSerializeField(path = "guiFill")
	private VaroMenuColor guiFiller = VaroMenuColor.GRAY;

	@VaroSerializeField(path = "guiSound")
	private String guiSoundName;

	@VaroSerializeField(path = "guiAnimation")
	private boolean guiAnimation = true;

	private AnimatedScoreboard scoreboard;
	private AnimatedTablist tablist;
	private AnimatedActionbar actionbar;
	private PlayerVersionAdapter versionAdapter;

	private VaroTeam team;
	private Sound guiSound = XSound.UI_BUTTON_CLICK.parseSound();
	private Player player;
	private ScoreboardInstance scoreboardInstance;
	private boolean alreadyHadMassProtectionTime, inMassProtectionTime, massRecordingKick;
	private ChatMessage lastMessage;

	public VaroPlayer() {
		varoplayer.add(this);
	}

	public VaroPlayer(Player player) {
		this.name = player.getName();
		this.uuid = player.getUniqueId().toString();
		this.id = generateId();

		this.stats = new Stats(this);
	}

	public VaroPlayer(String playerName, String uuid) {
		this.name = playerName;
		this.uuid = uuid;

		this.id = generateId();

		varoplayer.add(this);
		this.stats = new Stats(this);
		stats.loadDefaults();
	}

	private int generateId() {
		int id = JavaUtils.randomInt(1000, 9999999);
		while (getPlayer(id) != null)
			generateId();

		return id;
	}

	private void updateDiscordTeam(VaroTeam oldTeam) {
		VaroDiscordBot db = Main.getBotLauncher().getDiscordbot();
		if (db == null || !db.isEnabled())
			return;

		BotRegister reg = BotRegister.getBotRegisterByPlayerName(name);
		if (reg == null)
			return;

		Member member = reg.getMember();
		if (member == null)
			return;

		Guild guild = db.getMainGuild();
		if (guild == null)
		    return;
		
		UserSnowflake userSnowflake = User.fromId(member.getIdLong());
		if (oldTeam != null) {
			if (guild.getRolesByName("#" + oldTeam.getName(), true).size() > 0) {
				Role role = guild.getRolesByName("#" + oldTeam.getName(), true).get(0);
				guild.removeRoleFromMember(userSnowflake, role).complete();
			}
		}

		if (this.team != null) {
			Role role = guild.getRolesByName("#" + team.getName(), true).size() > 0 ? guild.getRolesByName("#" + team.getName(), true).get(0) : null;
			if (role == null)
				role = guild.createCopyOfRole(guild.getPublicRole()).setHoisted(true).setName("#" + team.getName()).complete();

			guild.addRoleToMember(userSnowflake, role).complete();
		}
	}

	/**
	 * @return Returns if a player is nearby
	 */
	public boolean canBeKicked(int noKickDistance) {
		if (noKickDistance < 1)
			return true;

		for (Entity entity : player.getNearbyEntities(noKickDistance, noKickDistance, noKickDistance)) {
			if (!(entity instanceof Player))
				continue;

			VaroPlayer vp = getPlayer((Player) entity);
			if (vp.equals(this))
				continue;

			if (vp.getTeam() != null)
				if (vp.getTeam().equals(team))
					continue;

			if (vp.getStats().isSpectator() || vp.isAdminIgnore())
				continue;

			return false;
		}

		return true;
	}

	public void cleanUpPlayer() {
		player.setHealth(player.getMaxHealth());
		player.setFoodLevel(20);
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		player.setExp(0);
		player.setLevel(0);
	}

	public void delete() {
		if (team != null)
			team.removeMember(this);

		if (rank != null)
			rank.remove();

		if (isOnline())
			player.kickPlayer(ConfigMessages.JOIN_KICK_NOT_USER_OF_PROJECT.getValue(this, this));

		if (villager != null)
			villager.remove();

		stats.remove();
		varoplayer.remove(this);
		Main.getVaroGame().getTopScores().update();
	}

	@Override
	public void onDeserializeEnd() {
		this.player = Bukkit.getPlayer(getRealUUID()) != null ? Bukkit.getPlayer(getRealUUID()) : null;
		this.guiSound = this.guiSoundName != null ? Sound.valueOf(this.guiSoundName) : null;
		if (this.player != null)
			setPlayer(this.player);
		if (isOnline()) {
			if (getStats().isSpectator() || isAdminIgnore())
				setSpectacting();

			setNormalAttackSpeed();
			LobbyItem.giveItems(player);
		} else if (isAdminIgnore())
			adminIgnore = false;

		this.stats.setOwner(this);
	}

	@Override
	public void onSerializeStart() {
		this.guiSoundName = this.guiSound != null ? this.guiSound.toString() : null;
	}

	public void onEvent(BukkitEventType type) {
		new BukkitEvent(this, type);
	}

	public void register() {
		if (this.stats == null)
			this.stats = new Stats(this);

		stats.loadDefaults();
		varoplayer.add(this);
	}

	public String getPrefix() {
		String pr = "";
		if (team != null)
			pr = team.getDisplay() + " ";

		if (rank != null)
			pr = rank.getDisplay() + (pr.isEmpty() ? " " : " §8| ") + pr;

		return pr;
	}

	public void setSpectacting() {
		new BukkitRunnable() {
			@Override
			public void run() {
				if (!isOnline())
					return;

				VersionUtils.getVersionAdapter().setXpCooldown(player, Integer.MAX_VALUE);
				new Vanish(player);
				player.setGameMode(GameMode.ADVENTURE);
				player.setAllowFlight(true);
				player.setFlying(true);
				player.setHealth(player.getMaxHealth());
				player.setFoodLevel(20);

				if (!adminIgnore) {
					player.getInventory().clear();
					player.getInventory().setArmorContents(new ItemStack[] {});
				}

				unregisterNametag(player, team);
				registerNametag(scoreboardInstance);
			}
		}.runTask(Main.getInstance());
	}

	public void setAlive() {
		new BukkitRunnable() {
			@Override
			public void run() {
				if (!isOnline())
					return;

				VersionUtils.getVersionAdapter().setXpCooldown(player, 0);
				player.getPlayer().setGameMode(GameMode.SURVIVAL);
				player.getPlayer().setAllowFlight(false);
				player.getPlayer().setFlying(false);
				cleanUpPlayer();

				Vanish v = Vanish.getVanish(player);
				if(v != null)
					v.remove();
				
				unregisterNametag(player, team);
				registerNametag(scoreboardInstance);
			}
		}.runTask(Main.getInstance());
	}

	public void update() {
		if (this.tablist != null)
			this.tablist.queueUpdate();

		if (this.scoreboard != null)
			this.scoreboard.queueUpdate();
		
		if (this.actionbar != null)
			this.actionbar.queueUpdate();

		if (this.player != null) {
			if (ConfigSetting.TABLIST_CHANGE_NAMES.getValueAsBoolean() && VersionUtils.getVersion().isHigherThan(ServerVersion.ONE_7))
				this.player.setPlayerListName(this.getTablistName());
			if (ConfigSetting.NAMETAGS_ENABLED.getValueAsBoolean()) {
				// afaik there is no event in Bukkit 1.8 that is executed when a player is given/removed a potion effect. Therefore this has to be executed on a regular basis (every second?) to avoid showing nametags when a player has the invisibility effect
				boolean potion = this.player.hasPotionEffect(PotionEffectType.INVISIBILITY);
				boolean visible = ConfigSetting.NAMETAGS_VISIBLE_DEFAULT.getValueAsBoolean() && !potion;
				String name = this.getNametagName();
				String prefix = this.getNametagPrefix();
				String suffix = this.getNametagSuffix();

				// always update default group
				Main.getDataManager().getDefaultNameTagGroup().update(this.player, visible, name, prefix, suffix);

				// only update spectator and team groups if necessary
				if (!ConfigSetting.NAMETAGS_VISIBLE_DEFAULT.getValueAsBoolean()) {
					if (ConfigSetting.NAMETAGS_VISIBLE_SPECTATOR.getValueAsBoolean())
						Main.getDataManager().getSpectatorNameTagGroup().update(this.player, true, name, prefix, suffix);
					if (ConfigSetting.NAMETAGS_VISIBLE_TEAM.getValueAsBoolean())
						for (VaroTeam team : VaroTeam.getOnlineTeams())
							team.getNameTagGroup().update(this.player, team == this.team && !potion, name, prefix, suffix);
				}
			}
		}
	}

	private String getTablistName() {
		if (ConfigSetting.TABLIST_CHANGE_NAMES.getValueAsBoolean()) {
			String listname = "";
			if (this.getTeam() != null) {
				if (this.getRank() == null) {
					listname = ConfigMessages.TABLIST_PLAYER_WITH_TEAM.getValue(null, this);
				} else {
					listname = ConfigMessages.TABLIST_PLAYER_WITH_TEAM_RANK.getValue(null, this);
				}
			} else {
				if (this.getRank() == null) {
					listname = ConfigMessages.TABLIST_PLAYER_WITHOUT_TEAM.getValue(null, this);
				} else {
					listname = ConfigMessages.TABLIST_PLAYER_WITHOUT_TEAM_RANK.getValue(null, this);
				}
			}

			if (ServerVersion.ONE_8.isHigherThan(VersionUtils.getVersion()) && listname.length() > 16)
				listname = listname.substring(0, 16);

			return listname;
		} else
			return player.getName();
	}
	
    private String getNametagName() {
    	int rankLocation = this.getRank() != null ? 9998 - this.getRank().getTablistLocation() : 9999;
    	String teamName = this.team != null ? this.team.getName() : "zzzzzzzz";
    	String format = String.format("%04d%-8.8s%.4s", rankLocation, teamName, this.player.getName()).replace(' ', 'z').toLowerCase();
    	return format;
    }

	private String getNametagPrefix() {
		String prefix = "";
		if (this.team != null)
			prefix = ConfigMessages.NAMETAG_PREFIX_TEAM.getValue(null, this);
		if (this.team == null || prefix.length() > 16)
			prefix = ConfigMessages.NAMETAG_PREFIX_NO_TEAM.getValue(null, this);
		if (prefix.length() > 16)
			prefix = prefix.substring(0, 16);
		return prefix;
	}

	private String getNametagSuffix() {
		String suffix = "";
		if (this.team != null)
			suffix = ConfigMessages.NAMETAG_SUFFIX_TEAM.getValue(null, this);
		if (this.team == null || suffix.length() > 16)
			suffix = ConfigMessages.NAMETAG_SUFFIX_NO_TEAM.getValue(null, this);
		if (suffix.length() > 16)
			suffix = suffix.substring(0, 16);
		return suffix;
	}

	public void saveTeleport(Location location) {
		SafeTeleport.tp(this.player, location);
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

	public AnimatedScoreboard getScoreboard() {
		return this.scoreboard;
	}
	
	public AnimatedActionbar getActionbar() {
		return this.actionbar;
	}

	public PlayerVersionAdapter getVersionAdapter() {
		return versionAdapter;
	}

	@Override
	public String getUUID() {
		return this.uuid;
	}

	@Override
	public String getLocale() {
		return this.locale == null && this.versionAdapter != null ? this.versionAdapter.getLocale() : this.locale;
	}

	public String setLocale(String locale) {
		return this.locale = locale;
	}

	@Override
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

	public OfflineVillager getVillager() {
		return villager;
	}

	public boolean isAdminIgnore() {
		return adminIgnore;
	}

	public boolean isInProtection() {
		if (VaroEvent.getEvent(VaroEventType.MASS_RECORDING).isEnabled()) {
			return inMassProtectionTime;
		} else {
			return ConfigSetting.PLAY_TIME.isIntActivated() && stats.getCountdown() >= (ConfigSetting.PLAY_TIME.getValueAsInt() * 60) - ConfigSetting.JOIN_PROTECTIONTIME.getValueAsInt() && Main.getVaroGame().isRunning() && !Main.getVaroGame().isFirstTime() && ConfigSetting.JOIN_PROTECTIONTIME.isIntActivated() && !isAdminIgnore();
		}
	}

	public boolean isMassRecordingKick() {
		return massRecordingKick;
	}

	public ChatMessage getLastMessage() {
		return lastMessage;
	}

	public void setLastMessage(ChatMessage lastMessage) {
		this.lastMessage = lastMessage;
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
		this.player.sendMessage(message);
	}

	public MessageHolder sendMessage(LoadableMessage message) {
		return super.sendTranslatedMessage(message, null, Main.getCuukyFrameWork().getPlaceholderManager(), Main.getCuukyFrameWork().getLanguageManager());
	}

	public MessageHolder sendMessage(LoadableMessage message, CustomPlayer replacement) {
		return super.sendTranslatedMessage(message, replacement, Main.getCuukyFrameWork().getPlaceholderManager(), Main.getCuukyFrameWork().getLanguageManager());
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

	public void setNormalAttackSpeed() {
		this.versionAdapter.setAttributeSpeed(!ConfigSetting.REMOVE_HIT_COOLDOWN.getValueAsBoolean() ? 4D : 100D);
	}

	public void setPlayer(Player player) {
		Player oldPlayer = this.player;
		this.player = player;

		if (player != null) {
			this.versionAdapter = new PlayerVersionAdapter(player);

			this.scoreboardInstance = ScoreboardInstance.newInstance(player);

			if (ConfigSetting.SCOREBOARD.getValueAsBoolean()) {
				this.scoreboard = new AnimatedScoreboard(Main.getInstance(), this.scoreboardInstance, Main.getDataManager().getScoreboardConfig().getTitle(), Main.getDataManager().getScoreboardConfig().getScoreboard()) {
					@Override
					protected String processString(String input) {
						return VaroPlayer.this.replacePlaceHolders(input);
					}
				};
				this.scoreboard.setEnabled(this.stats.isShowScoreboard());
			}

			if (VersionUtils.getVersion().isHigherThan(ServerVersion.ONE_7) && ConfigSetting.TABLIST.getValueAsBoolean() && (ConfigSetting.TABLIST_USE_HEADER.getValueAsBoolean() || ConfigSetting.TABLIST_USE_FOOTER.getValueAsBoolean())) {
				this.tablist = new AnimatedTablist(Main.getInstance(), this.getPlayer(), Main.getDataManager().getTablistConfig().getHeader(), Main.getDataManager().getTablistConfig().getFooter()) {
					@Override
					protected String processString(String input) {
						return VaroPlayer.this.replacePlaceHolders(input);
					}
				};

				this.tablist.setHeaderEnabled(ConfigSetting.TABLIST_USE_HEADER.getValueAsBoolean());
				this.tablist.setFooterEnabled(ConfigSetting.TABLIST_USE_FOOTER.getValueAsBoolean());
			}
			
			if (VersionUtils.getVersion().isHigherThan(ServerVersion.ONE_7) && ConfigSetting.ACTIONBAR.getValueAsBoolean()) {
				this.actionbar = new AnimatedActionbar(Main.getInstance(), player, Main.getDataManager().getActionbarConfig().getContent()) {
					@Override
					protected String processString(String input) {
						return VaroPlayer.this.replacePlaceHolders(input);
					}
				};
				this.actionbar.setEnabled(this.stats.isShowActionbar());
			}

			this.registerNametag(this.scoreboardInstance);
		} else {
			if (this.scoreboard != null)
				this.scoreboard.destroy();
			if (this.tablist != null)
				this.tablist.destroy();
			if (this.actionbar != null)
				this.actionbar.destroy();
			if(oldPlayer != null)
				this.unregisterNametag(oldPlayer, this.team);

			this.scoreboardInstance = null;
			this.versionAdapter = null;
			this.scoreboard = null;
			this.tablist = null;
		}
	}

	private void registerNametag(ScoreboardInstance scoreboardInstance) {
		if (ConfigSetting.NAMETAGS_ENABLED.getValueAsBoolean()) {
			String name = this.getNametagName();
			String prefix = this.getNametagPrefix();
			String suffix = this.getNametagSuffix();

			if (ConfigSetting.NAMETAGS_VISIBLE_DEFAULT.getValueAsBoolean())
				Main.getDataManager().getDefaultNameTagGroup().register(scoreboardInstance, !this.player.hasPotionEffect(PotionEffectType.INVISIBILITY), name, prefix, suffix);
			else {
				if (ConfigSetting.NAMETAGS_VISIBLE_SPECTATOR.getValueAsBoolean() && this.getStats().isSpectator())
					Main.getDataManager().getSpectatorNameTagGroup().register(scoreboardInstance, true, name, prefix, suffix);
				else if (ConfigSetting.NAMETAGS_VISIBLE_TEAM.getValueAsBoolean() && this.getTeam() != null)
					this.getTeam().getNameTagGroup().register(scoreboardInstance, true, name, prefix, suffix);
				else
					Main.getDataManager().getDefaultNameTagGroup().register(scoreboardInstance, false, name, prefix, suffix);
			}
		}
	}

	private void unregisterNametag(Player player, VaroTeam team) {
		Main.getDataManager().getDefaultNameTagGroup().unRegister(player);
		Main.getDataManager().getSpectatorNameTagGroup().unRegister(player);
		if (team != null)
			team.getNameTagGroup().unRegister(player);
	}

	private String replacePlaceHolders(String input) {
		return Main.getLanguageManager().replaceMessage(input, VaroPlayer.this);
	}

	public void setRank(Rank rank) {
		this.rank = rank;

		if (isOnline())
			update();
	}

	public void setGuiFiller(VaroMenuColor guiFiller) {
		this.guiFiller = guiFiller;
	}

	public VaroMenuColor getGuiFiller() {
		return guiFiller;
	}

	public void setGuiSound(Sound guiSound) {
		this.guiSound = guiSound;
	}

	public Sound getGuiSound() {
		return guiSound;
	}

	public void setGuiAnimation(boolean guiAnimation) {
		this.guiAnimation = guiAnimation;
	}

	public boolean hasGuiAnimation() {
		return guiAnimation;
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

		if (!Main.isBootedUp())
			return;

		try {
			if (ConfigSetting.DISCORDBOT_SET_TEAM_AS_GROUP.getValueAsBoolean()) {
				if (Main.getBotLauncher() == null)
					new BukkitRunnable() {
						@Override
						public void run() {
							updateDiscordTeam(oldTeam);
						}
					}.runTaskLaterAsynchronously(Main.getInstance(), 1L);
				else
					updateDiscordTeam(oldTeam);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (isOnline()) {
			update();
			LobbyItem.giveOrRemoveTeamItems(this);
			
			this.unregisterNametag(this.player, oldTeam);
			this.registerNametag(this.scoreboardInstance);
		}

		// Main#getVaroGame may not be initialized yet
		if (Main.getVaroGame() != null)
			Main.getVaroGame().getTopScores().update();
	}

	@Override
	public BoardUpdateHandler<VaroPlayer> getUpdateHandler() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @return Returns all alive Players regardless if they are online
	 */
	public static ArrayList<VaroPlayer> getAlivePlayer() {
		ArrayList<VaroPlayer> alive = new ArrayList<>();
		for (VaroPlayer vp : varoplayer) {
			if (!vp.getStats().isAlive())
				continue;

			alive.add(vp);
		}

		return alive;
	}

	public static ArrayList<VaroPlayer> getDeadPlayer() {
		ArrayList<VaroPlayer> dead = new ArrayList<>();
		for (VaroPlayer vp : varoplayer) {
			if (vp.getStats().getState() != PlayerState.DEAD)
				continue;

			dead.add(vp);
		}

		return dead;
	}

	public static ArrayList<VaroPlayer> getOnlineAndAlivePlayer() {
		ArrayList<VaroPlayer> online = new ArrayList<>();
		for (VaroPlayer vp : varoplayer) {
			if (!vp.isOnline() || !vp.getStats().isAlive())
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
		for (VaroPlayer vp : varoplayer) {
			if (!vp.isOnline())
				continue;

			online.add(vp);
		}

		return online;
	}

	public static VaroPlayer getPlayer(int id) {
		for (VaroPlayer vp : varoplayer) {
			if (vp.getId() != id)
				continue;

			return vp;
		}

		return null;
	}

	/**
	 * @return Returns the varoplayer and sets the name right if the player changed it before
	 */
	public static VaroPlayer getPlayer(Player player) {
		for (VaroPlayer vp : varoplayer) {
			if (vp.getUUID() != null)
				if (!vp.getUUID().equals(player.getUniqueId().toString()))
					continue;

			if (vp.getUUID() == null && player.getName().equalsIgnoreCase(vp.getName()))
				vp.setUuid(player.getUniqueId().toString());
			else if (vp.getUUID() == null)
				continue;

			if (!vp.getName().equals(player.getName())) {
				Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.ALERT, ConfigMessages.ALERT_SWITCHED_NAME.getValue(null, vp).replace("%newName%", player.getName()), player.getUniqueId());
				Bukkit.broadcastMessage("§c" + vp.getName() + " §7hat seinen Namen gewechselt und ist nun unter §c" + player.getName() + " §7bekannt!");
				new Alert(AlertType.NAME_SWITCH, vp.getName() + " §7hat seinen Namen gewechselt und ist nun unter §c" + player.getName() + " §7bekannt!");
				vp.setName(player.getName());
			}

			return vp;
		}

		return null;
	}

	// This has to be one of the worst methods in the entire plugin. But it's called so often that I don't feel like fixing it.
	// 'name' can either be the actual name of a player or their UUID (as string format!!!). It should just accept a UUID!!! DON'T USE NAMES TO IDENTIFY A PLAYER!!!
	// Also why tf does this use continue instead of just returning the player immediately
	public static VaroPlayer getPlayer(String name) {
		for (VaroPlayer vp : varoplayer) {
			if (!vp.getName().equalsIgnoreCase(name) && !vp.getUUID().equals(name))
				continue;

			return vp;
		}

		return null;
	}

	public static ArrayList<VaroPlayer> getSpectator() {
		ArrayList<VaroPlayer> spectator = new ArrayList<>();
		for (VaroPlayer vp : varoplayer) {
			if (!vp.getStats().isSpectator())
				continue;

			spectator.add(vp);
		}

		return spectator;
	}

	public static ArrayList<VaroPlayer> getVaroPlayers() {
		return varoplayer;
	}
}