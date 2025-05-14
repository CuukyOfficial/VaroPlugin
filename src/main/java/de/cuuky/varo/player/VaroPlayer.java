package de.cuuky.varo.player;

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

import de.cuuky.varo.Main;
import de.cuuky.varo.alert.Alert;
import de.cuuky.varo.alert.AlertType;
import de.cuuky.varo.bot.discord.BotRegister;
import de.cuuky.varo.bot.discord.VaroDiscordBot;
import de.cuuky.varo.config.language.Messages;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.event.VaroEvent;
import de.cuuky.varo.event.VaroEventType;
import de.cuuky.varo.game.LobbyItem;
import de.cuuky.varo.gui.settings.VaroMenuColor;
import de.cuuky.varo.listener.helper.ChatMessage;
import de.cuuky.varo.logger.logger.EventLogger.LogType;
import de.cuuky.varo.player.event.BukkitEvent;
import de.cuuky.varo.player.event.BukkitEventType;
import de.cuuky.varo.player.stats.Stats;
import de.cuuky.varo.player.stats.stat.PlayerState;
import de.cuuky.varo.player.stats.stat.Rank;
import de.cuuky.varo.player.stats.stat.offlinevillager.OfflineVillager;
import de.cuuky.varo.serialize.identifier.VaroSerializeField;
import de.cuuky.varo.serialize.identifier.VaroSerializeable;
import de.cuuky.varo.team.VaroTeam;
import de.cuuky.varo.utils.Vanish;
import de.varoplugin.cfw.player.PlayerVersionAdapter;
import de.varoplugin.cfw.player.SafeTeleport;
import de.varoplugin.cfw.player.hud.AnimatedActionbar;
import de.varoplugin.cfw.player.hud.AnimatedScoreboard;
import de.varoplugin.cfw.player.hud.AnimatedTablist;
import de.varoplugin.cfw.player.hud.AnimationData;
import de.varoplugin.cfw.player.hud.ScoreboardInstance;
import de.varoplugin.cfw.utils.JavaUtils;
import de.varoplugin.cfw.version.ServerVersion;
import de.varoplugin.cfw.version.VersionUtils;
import io.github.almightysatan.slams.Placeholder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.UserSnowflake;

public class VaroPlayer implements VaroSerializeable {

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

	@Deprecated
	@VaroSerializeField(path = "locale")
	private String locale;
	
	@VaroSerializeField(path = "language")
    private String language;

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
	private Sound guiSound = XSound.UI_BUTTON_CLICK.get();
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
		this.stats.loadDefaults();
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
		int id;

		do {
			id = JavaUtils.randomInt(1000, 9999999);
		} while (getPlayer(id) != null);

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
		    Messages.PLAYER_KICK_NOT_USER_OF_PROJECT.kick(this);

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
		varoplayer.add(this);
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
			String listname = Messages.PLAYER_TABLIST_FORMAT.value(this);

			// max 16 characters on 1.7.10
			if (ServerVersion.ONE_8.isHigherThan(VersionUtils.getVersion()) && listname.length() > 16)
				listname = listname.substring(0, 16);

			return listname;
		}
        return player.getName();
	}
	
    private String getNametagName() {
    	int rankLocation = this.getRank() != null ? 9998 - this.getRank().getTablistLocation() : 9999;
    	String teamName = this.team != null ? this.team.getName() : "zzzzzzzz";
    	String format = String.format("%04d%-8.8s%.4s", rankLocation, teamName, this.player.getName()).replace(' ', 'z').toLowerCase();
    	return format;
    }

	private String getNametagPrefix() {
		String prefix = Messages.PLAYER_NAMETAG_PREFIX.value(this);
		if (prefix.length() > 16)
			prefix = prefix.substring(0, 16);
		return prefix;
	}

	private String getNametagSuffix() {
		String suffix = Messages.PLAYER_NAMETAG_SUFFIX.value(this);
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
	
	public String getDisplayName() {
	    return Messages.PLAYER_DISPLAYNAME.value(this);
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

	public String getUUID() {
		return this.uuid;
	}

	public String getLanguage() {
	    if (!ConfigSetting.LANGUAGE_ALLOW_OTHER.getValueAsBoolean())
	        return ConfigSetting.LANGUAGE_DEFAULT.getValueAsString();

	    String language = this.language;
	    if (language != null)
	        return language;
	    
	    PlayerVersionAdapter versionAdapter = this.versionAdapter;
	    if (versionAdapter != null) {
	        String locale = versionAdapter.getLocale();
	        System.out.println(locale);
	        if (locale.startsWith("en_"))
	            return Messages.LANGUAGE_EN;
	        else if (locale.startsWith("de_"))
	            return Messages.LANGUAGE_DE;
	    }
	    return Messages.LANGUAGE_DEFAULT;
	}
	
	public void setLanguage(String language) {
        this.language = language;
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

	public OfflineVillager getVillager() {
		return villager;
	}

	public boolean isAdminIgnore() {
		return adminIgnore;
	}

	public boolean isInProtection() {
		if (VaroEvent.getEvent(VaroEventType.MASS_RECORDING).isEnabled()) {
			return inMassProtectionTime;
		}
        return Main.getVaroGame().isPlayTimeLimited() && stats.getCountdown() >= (Main.getVaroGame().getPlayTime() * 60) - ConfigSetting.JOIN_PROTECTIONTIME.getValueAsInt() && Main.getVaroGame().isRunning() && !Main.getVaroGame().isFirstTime() && ConfigSetting.JOIN_PROTECTIONTIME.isIntActivated() && !isAdminIgnore();
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

			if (ConfigSetting.SCOREBOARD_ENABLED.getValueAsBoolean()) {
				this.scoreboard = new AnimatedScoreboard(Main.getInstance(), this.scoreboardInstance, new AnimationData<String>() {
                    
                    @Override
                    public int getNumFrames() {
                        return Messages.PLAYER_SCOREBOARD_TITLE.size(VaroPlayer.this);
                    }
                    
                    @Override
                    public String getFrame(int index) {
                        return Messages.PLAYER_SCOREBOARD_TITLE.value(index, VaroPlayer.this);
                    }
                    
                    @Override
                    public int getDelay() {
                        return ConfigSetting.SCOREBOARD_TITLE_UPDATEDELAY.getValueAsInt();
                    }
                }, new AnimationData<String[]>() {
                    
                    @Override
                    public int getNumFrames() {
                        return Messages.PLAYER_SCOREBOARD_CONTENT.size(VaroPlayer.this);
                    }
                    
                    @Override
                    public String[] getFrame(int index) {
                        return Messages.PLAYER_SCOREBOARD_CONTENT.value(index, VaroPlayer.this);
                    }
                    
                    @Override
                    public int getDelay() {
                        return ConfigSetting.SCOREBOARD_BODY_UPDATEDELAY.getValueAsInt();
                    }
                });
				this.scoreboard.setEnabled(this.stats.isShowScoreboard());
			}

			if (VersionUtils.getVersion().isHigherThan(ServerVersion.ONE_7) && ConfigSetting.TABLIST_ENABLED.getValueAsBoolean() && (ConfigSetting.TABLIST_HEADER_ENABLED.getValueAsBoolean() || ConfigSetting.TABLIST_FOOTER_ENABLED.getValueAsBoolean())) {
				this.tablist = new AnimatedTablist(Main.getInstance(), this.getPlayer(), new AnimationData<String>() {
                    
				    @Override
                    public int getNumFrames() {
                        return Messages.PLAYER_TABLIST_HEADER.size(VaroPlayer.this);
                    }
                    
                    @Override
                    public String getFrame(int index) {
                        return String.join("\n", Messages.PLAYER_TABLIST_HEADER.value(index, VaroPlayer.this)); // TODO this should not be done on every update
                    }
                    
                    @Override
                    public int getDelay() {
                        return ConfigSetting.TABLIST_HEADER_UPDATEDELAY.getValueAsInt();
                    }
                }, new AnimationData<String>() {
                    
                    @Override
                    public int getNumFrames() {
                        return Messages.PLAYER_TABLIST_FOOTER.size(VaroPlayer.this);
                    }
                    
                    @Override
                    public String getFrame(int index) {
                        return String.join("\n", Messages.PLAYER_TABLIST_FOOTER.value(index, VaroPlayer.this)); // TODO this should not be done on every update
                    }
                    
                    @Override
                    public int getDelay() {
                        return ConfigSetting.TABLIST_FOOTER_UPDATEDELAY.getValueAsInt();
                    }
                });

				this.tablist.setHeaderEnabled(ConfigSetting.TABLIST_HEADER_ENABLED.getValueAsBoolean());
				this.tablist.setFooterEnabled(ConfigSetting.TABLIST_FOOTER_ENABLED.getValueAsBoolean());
			}
			
			if (VersionUtils.getVersion().isHigherThan(ServerVersion.ONE_7) && ConfigSetting.ACTIONBAR_ENABLED.getValueAsBoolean()) {
				this.actionbar = new AnimatedActionbar(Main.getInstance(), player, new AnimationData<String>() {
                    
				    @Override
                    public int getNumFrames() {
                        return Messages.PLAYER_ACTIONBAR.size(VaroPlayer.this);
                    }
                    
                    @Override
                    public String getFrame(int index) {
                        return Messages.PLAYER_ACTIONBAR.value(index, VaroPlayer.this);
                    }
                    
                    @Override
                    public int getDelay() {
                        return ConfigSetting.ACTIONBAR_UPDATEDELAY.getValueAsInt();
                    }
                });
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
				Messages.LOG_SWITCHED_NAME.log(LogType.ALERT, vp, Placeholder.constant("new-name", player.getName()));
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