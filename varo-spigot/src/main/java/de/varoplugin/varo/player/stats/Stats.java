package de.varoplugin.varo.player.stats;

import de.varoplugin.cfw.location.SimpleLocationFormat;
import de.varoplugin.cfw.version.VersionUtils;
import de.varoplugin.varo.Main;
import de.varoplugin.varo.alert.Alert;
import de.varoplugin.varo.alert.AlertType;
import de.varoplugin.varo.api.player.PlayerStateChangeEvent;
import de.varoplugin.varo.api.player.strike.PlayerStrikeReceiveEvent;
import de.varoplugin.varo.api.player.strike.PlayerStrikeRemoveEvent;
import de.varoplugin.varo.config.VaroConfig;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;
import de.varoplugin.varo.event.VaroEvent;
import de.varoplugin.varo.event.VaroEventType;
import de.varoplugin.varo.game.WinnerCheck;
import de.varoplugin.varo.logger.logger.EventLogger.LogType;
import de.varoplugin.varo.player.VaroPlayer;
import de.varoplugin.varo.player.stats.stat.PlayerState;
import de.varoplugin.varo.player.stats.stat.Strike;
import de.varoplugin.varo.player.stats.stat.StrikeTemplate;
import de.varoplugin.varo.player.stats.stat.YouTubeVideo;
import de.varoplugin.varo.player.stats.stat.inventory.InventoryBackup;
import de.varoplugin.varo.player.stats.stat.inventory.VaroSaveable;
import de.varoplugin.varo.serialize.identifier.VaroSerializeField;
import de.varoplugin.varo.serialize.identifier.VaroSerializeable;
import de.varoplugin.varo.spawns.Spawn;
import de.varoplugin.varo.utils.EventUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Stats implements VaroSerializeable {

	@VaroSerializeField(path = "state")
	private PlayerState state;
	@VaroSerializeField(path = "lastLocation")
	private Location lastLocation;

	@VaroSerializeField(path = "countdown")
	private int countdown;
	@VaroSerializeField(path = "sessionTime")
	private int sessionTime;
	@VaroSerializeField(path = "onlineTime")
	private int onlineTime;
	@VaroSerializeField(path = "onlineTimeTotal")
	private int onlineTimeTotal;
	@VaroSerializeField(path = "kills")
	private int kills;
	@VaroSerializeField(path = "sessions")
	private int sessions;
	@VaroSerializeField(path = "sessionsPlayed")
	private int sessionsPlayed;
	@VaroSerializeField(path = "wins")
	private int wins;

	@VaroSerializeField(path = "showActionbar")
	private boolean showActionbar;
	@VaroSerializeField(path = "showScoreboard")
	private boolean showScoreboard;
	@VaroSerializeField(path = "willClear")
	private boolean willClear;

	@VaroSerializeField(path = "onlineAfterStart")
	private boolean onlineAfterStart;
	@VaroSerializeField(path = "firstTimeJoined")
	private Date firstTimeJoined;
	@VaroSerializeField(path = "lastJoined5")
	private OffsetDateTime lastJoined;
	@VaroSerializeField(path = "lastEnemyContact")
	private Date lastEnemyContact;
	@VaroSerializeField(path = "diedAt")
	private Date diedAt;
	@VaroSerializeField(path = "timeUntilAddSession")
	private Date timeUntilAddSession;

	@VaroSerializeField(path = "playerBackpack")
	private VaroInventory playerBackpack;
	@VaroSerializeField(path = "restoreBackup")
	private InventoryBackup restoreBackup;
	@VaroSerializeField(path = "youtubeLink")
	private String youtubeLink;

	@VaroSerializeField(path = "inventoryBackups", arrayClass = InventoryBackup.class)
	private ArrayList<InventoryBackup> inventoryBackups;
	@VaroSerializeField(path = "saveables", arrayClass = VaroSaveable.class)
	private ArrayList<VaroSaveable> saveables;
	@VaroSerializeField(path = "strikes5", arrayClass = Strike.class)
	private ArrayList<Strike> strikes;
	@VaroSerializeField(path = "videos", arrayClass = YouTubeVideo.class)
	private ArrayList<YouTubeVideo> videos;

	private VaroPlayer owner;

	public Stats() {} // Serialization

	public Stats(VaroPlayer vp) {
		this.owner = vp;
	}

	public void addInventoryBackup(InventoryBackup backup) {
		inventoryBackups.add(backup);
	}

	public void addKill() {
		this.kills++;

		if (owner.isOnline())
			owner.update();

		Main.getVaroGame().getTopScores().update();
	}

	public void addSaveable(VaroSaveable saveable) {
		saveables.add(saveable);
	}

	public void addSessionPlayed() {
		sessionsPlayed++;
	}

	public void strike(String reason, String operator) {
		List<StrikeTemplate> templates = VaroConfig.STRIKE_TEMPLATES.getValue();
		StrikeTemplate template = templates.get(Math.min(this.strikes.size(), templates.size() - 1));
		Strike strike = new Strike(template, reason, this.owner, operator);
		if (EventUtils.callEvent(new PlayerStrikeReceiveEvent(strike)))
			return;

		this.strikes.add(strike);
		strike.activate();

		new Alert(AlertType.STRIKE, this.owner.getName() + " hat einen Strike erhalten! " + this.owner.getName() + " hat jetzt " + strikes.size());
	}

	public void addVideo(YouTubeVideo video) {
		videos.add(video);

		Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.YOUTUBE, owner.getName() + " hat heute folgendes Projektvideo hochgeladen: " + video.getLink(), owner.getRealUUID());
	}

	public void addWin() {
		wins++;
	}

	public void clearInventory() {
		if (owner.isOnline()) {
			owner.getPlayer().getInventory().clear();

			if (VaroConfig.STRIKE_CLEAR_ARMOR.getValue()) {
				for (ItemStack stack : owner.getPlayer().getInventory().getArmorContents())
					if (stack != null)
						stack.setType(Material.AIR);
			}

			owner.getPlayer().getInventory().setArmorContents(new ItemStack[] {}); // TODO ???
		} else
			setWillClear(true);

		new Alert(AlertType.INVENTORY_CLEAR, this.owner.getName() + "'s Inventar wurde geleert!");
	}

	public int getCountdown() {
		return this.countdown;
	}

	public int getSessionTime() {
		return this.sessionTime;
	}

	public int getOnlineTime() {
		return this.onlineTime;
	}
	
	public int getOnlineTimeTotal() {
		return this.onlineTimeTotal;
	}

	public Date getDiedAt() {
		return diedAt;
	}

	public boolean isOnlineAfterStart() {
		return onlineAfterStart;
	}

	public Date getFirstTimeJoined() {
		return firstTimeJoined;
	}

	public ArrayList<InventoryBackup> getInventoryBackups() {
		return inventoryBackups;
	}

	// TODO this belongs in PlayerLoginListener
	public KickResult getKickResult(Player player) {
        if (Bukkit.hasWhitelist() && !Bukkit.getWhitelistedPlayers().contains(player))
            return KickResult.SERVER_NOT_PUBLISHED;
        
        if (VersionUtils.getVersionAdapter().getOnlinePlayers().size() >= Bukkit.getMaxPlayers())
            return KickResult.SERVER_FULL;
	    
		KickResult result = KickResult.ALLOW;
		if (Main.getVaroGame().hasStarted()) {
			if ((ConfigSetting.UNREGISTERED_PLAYER_JOIN_DURING_GAME.getValueAsBoolean() && !Main.getVaroGame().hasEnded()) || this.owner.isRegistered())
				result = getVaroKickResult();
			else
				result = KickResult.NO_PROJECTUSER;
		} else {
			if (!ConfigSetting.UNREGISTERED_PLAYER_JOIN.getValueAsBoolean() && !owner.isRegistered())
				result = KickResult.NO_PROJECTUSER;

			if (Main.getVaroGame().isStarting() && Spawn.getSpawn(owner) == null)
				result = KickResult.NO_PROJECTUSER;
		}

		return result;
	}

	public int getKills() {
		return kills;
	}

	public Date getLastEnemyContact() {
		return lastEnemyContact;
	}

	public OffsetDateTime getLastJoined() {
		return lastJoined;
	}

	public Location getLastLocation() {
		return lastLocation;
	}

	public String getName() {
		return owner.getName();
	}

	public VaroInventory getPlayerBackpack() {
		return playerBackpack;
	}

	public InventoryBackup getRestoreBackup() {
		return restoreBackup;
	}

	public ArrayList<VaroSaveable> getSaveables() {
		if (owner.getTeam() != null)
			return owner.getTeam().getSaveables();
        return saveables;
	}

	public ArrayList<VaroSaveable> getSaveablesRaw() {
		return saveables;
	}

	public int getSessions() {
		return sessions;
	}

	public int getSessionsPlayed() {
		return sessionsPlayed;
	}

	public PlayerState getState() {
		return state;
	}

	public String[] getStatsListed() {
		String colorcode = Main.getColorCode();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		this.lastLocation = owner.isOnline() ? owner.getPlayer().getLocation() : lastLocation;

		return new String[] { "§7ID§8: " + colorcode + owner.getId(), "§7UUID§8: " + colorcode + owner.getUUID(),
				"§7Team§8: " + colorcode + (owner.getTeam() != null ? owner.getTeam().getDisplayName() : "/"),
				"§7Rank§8: " + colorcode + (owner.getRank() != null ? owner.getRank().getDisplay() : "/"),
				"§7Sessions§8: " + colorcode + sessions, "§7Sessions Played§8: " + colorcode + sessionsPlayed,
				"§7Countdown§8: " + colorcode + countdown,
				"§7SessionTime§8: " + colorcode + this.sessionTime,
				"§7OnlineTime§8: " + colorcode + this.onlineTime,
				"§7OnlineTimeTotal§8: " + colorcode + this.onlineTimeTotal,
				"§7Kills§8: " + colorcode + kills,
				"§7WillClearInventory§8: " + colorcode + willClear,
				"§7ShowScoreboard§8: " + colorcode + showScoreboard,
				"§7ShowActionbar§8: " + colorcode + this.showActionbar,
				"§7LastLocation§8: " + colorcode + (lastLocation != null ? new SimpleLocationFormat(colorcode + "x§7, " + colorcode + "y§7, " + colorcode + "z§7 in " + colorcode + "world").format(lastLocation) : "/"),
				"§7TimeUntilAddSession§8: " + colorcode + (timeUntilAddSession != null ? dateFormat.format(timeUntilAddSession.getTime()) : "/"),
				"§7OnlineAfterStart§8: " + colorcode + onlineAfterStart,
				"§7FirstTimeJoined§8: " + colorcode + (firstTimeJoined != null ? dateFormat.format(firstTimeJoined) : "/"),
				"§7LastTimeJoined§8: " + colorcode + (lastJoined != null ? dateFormat.format(lastJoined) : "/"),
				"§7LastEnemyContact§8: " + colorcode + (lastEnemyContact != null ? dateFormat.format(lastEnemyContact) : "/"),
				"§7DiedAt§8: " + colorcode + (diedAt == null ? "/" : dateFormat.format(diedAt)),
				"§7YouTubeLink§8: " + colorcode + (youtubeLink != null ? youtubeLink : "/"),
				"§7YouTubeVideos§8: " + colorcode + (videos == null ? 0 : videos.size()),
				"§7StrikeAmount§8: " + colorcode + (strikes == null ? 0 : strikes.size()),
				"§7State§8: " + colorcode + state.getName() };
	}

	public ArrayList<Strike> getStrikes() {
		return strikes;
	}

	public Date getTimeUntilAddSession() {
		return timeUntilAddSession;
	}

	public KickResult getVaroKickResult() {
		GregorianCalendar curr = new GregorianCalendar();
		KickResult result = KickResult.ALLOW;
		if (this.sessions > 0) {
			result = KickResult.ALLOW;
		} else {
			if (ConfigSetting.SESSIONS_PER_DAY.getValueAsInt() > 0) {
				if (ConfigSetting.PRE_PRODUCE_SESSIONS.getValueAsInt() > 0) {
					result = KickResult.NO_PREPRODUCES_LEFT;
				} else {
					result = KickResult.NO_SESSIONS_LEFT;
				}
			} else {
				result = KickResult.NO_TIME;
			}
		}

		if (VaroEvent.getEvent(VaroEventType.MASS_RECORDING).isEnabled())
			result = KickResult.MASS_RECORDING_JOIN;

		if (Main.getVaroGame().isFinaleJoin()) {
			result = KickResult.FINALE_JOIN;
		}

		if (Main.isBootedUp())
			if (!Main.getDataManager().getOutsideTimeChecker().canJoin())
				result = KickResult.NOT_IN_TIME;

		for (Strike strike : this.strikes)
			if (strike.getBanUntil() != null && curr.before(strike.getBanUntil())) {
				result = KickResult.STRIKE_BAN;
				break;
			}

		if (!this.isAlive())
			result = KickResult.DEAD;

		if (isSpectator())
			result = KickResult.SPECTATOR;

		return result;
	}

	public int getWins() {
		return wins;
	}

	public String getYoutubeLink() {
		return youtubeLink;
	}

	public boolean hasFullTime() {
		return countdown == Main.getVaroGame().getPlayTime() * 60;
	}

	public boolean hasTimeLeft() {
		return (this.countdown >= 1) && this.countdown != Main.getVaroGame().getPlayTime() * 60;
	}

	public boolean hasVideo(String videoId) {
		for (YouTubeVideo video : videos)
			if (video.getVideoId().equals(videoId))
				return true;

		return false;
	}

	public ArrayList<YouTubeVideo> getVideos() {
		return videos;
	}

	public boolean isAlive() {
		return this.state == PlayerState.ALIVE;
	}

	public boolean isDead() {
        return this.state == PlayerState.DEAD;
    }

	public boolean isShowActionbar() {
		return showActionbar;
	}

	public boolean isShowScoreboard() {
		return showScoreboard;
	}

	public boolean isSpectator() {
		return state == PlayerState.SPECTATOR;
	}

	public boolean isWillClear() {
		return willClear;
	}

	public void loadDefaults() {
		loadStartDefaults();
		kills = 0;
		youtubeLink = null;
		wins = 0;
		state = PlayerState.ALIVE;
		onlineAfterStart = false;

		removeTeamAndRank();

		lastLocation = null;
		if (owner.isOnline()) {
			new BukkitRunnable() {
				@Override
				public void run() {
				    Player player = owner.getPlayer();
				    if (player != null)
				        lastLocation = player.getLocation();
				}
			}.runTaskLater(Main.getInstance(), 1L);
		}

	}

	public void loadStartDefaults() {
		if (owner.getTeam() != null)
			owner.getTeam().loadDefaults();

		videos = new ArrayList<YouTubeVideo>();
		strikes = new ArrayList<Strike>();
		saveables = new ArrayList<VaroSaveable>();
		inventoryBackups = new ArrayList<InventoryBackup>();
		playerBackpack = new VaroInventory(ConfigSetting.BACKPACK_PLAYER_SIZE.getValueAsInt());

		willClear = false;
		showScoreboard = true;
		this.showActionbar = true;
		diedAt = null;
		timeUntilAddSession = null;

		onlineAfterStart = owner.isOnline();
		firstTimeJoined = new Date();
		lastJoined = OffsetDateTime.now();
		lastEnemyContact = new Date();
		if (ConfigSetting.SESSIONS_PER_DAY.getValueAsInt() > 0) {
			sessions = ConfigSetting.SESSIONS_PER_DAY.getValueAsInt();
		} else {
			sessions = 1;
		}
		if (ConfigSetting.PRE_PRODUCE_SESSIONS.getValueAsInt() > 0) {
			sessions += ConfigSetting.PRE_PRODUCE_SESSIONS.getValueAsInt();
		}
		sessionsPlayed = 0;
		countdown = Main.getVaroGame().getPlayTime() * 60;
		this.sessionTime = 0;
		this.onlineTime = 0;
		this.onlineTimeTotal = 0;
	}

	@Override
	public void onDeserializeEnd() {
		if (this.strikes == null) // Backwards compatibility with v4
			this.strikes = new ArrayList<>();
	}

	@Override
	public void onSerializeStart() {}

	public void remove() {
		if (videos != null)
			videos.forEach(video -> video.remove());

		if (saveables != null)
			saveables.forEach(saveable -> saveable.remove());

		if (inventoryBackups != null)
			inventoryBackups.forEach(b -> b.remove());

		setState(PlayerState.DEAD);
	}

	public void removeCountdown() {
		this.countdown = Main.getVaroGame().getPlayTime() * 60;
		this.sessionTime = 0;
		this.onlineTime = 0;
	}

	public void removeInventoryBackup(InventoryBackup backup) {
		backup.remove();
		inventoryBackups.remove(backup);
	}

	public void removeSaveable(VaroSaveable saveable) {
		saveables.remove(saveable);
	}

	public void removeStrike(Strike strike) {
		if (EventUtils.callEvent(new PlayerStrikeRemoveEvent(strike)))
			return;

		this.strikes.remove(strike);
	}

	public void removeTeamAndRank() {
		if (owner.getTeam() != null)
			owner.getTeam().removeMember(owner);

		if (owner.getRank() != null)
			owner.setRank(null);
	}

	public void removeVideo(YouTubeVideo video) {
		videos.remove(video);
	}

	public void removeReamainingSession() {
		sessions--;
		this.sessionTime = 0;

		if (ConfigSetting.SESSIONS_PER_DAY.getValueAsInt() <= 0) {
			timeUntilAddSession = DateUtils.addHours(new Date(), ConfigSetting.JOIN_AFTER_HOURS.getValueAsInt());
		}
	}

	public void setCountdown(int time) {
		this.countdown = time;
	}
	
	public void setSessionTime(int sessionTime) {
		this.sessionTime = sessionTime;
	}

	public void setOnlineTime(int onlineTime) {
		this.onlineTime = onlineTime;
	}
	
	public void setOnlineTimeTotal(int onlineTimeTotal) {
		this.onlineTimeTotal = onlineTimeTotal;
	}
	
	public void increaseOnlineTime() {
		this.sessionTime++;
		this.onlineTime++;
		this.onlineTimeTotal++;
	}

	public void setDiedAt(Date diedAt) {
		this.diedAt = diedAt;
	}

	public void setOnlineAfterStart() {
		this.onlineAfterStart = true;
	}

	public void setFirstTimeJoined(Date firstTimeJoined) {
		this.firstTimeJoined = firstTimeJoined;
	}

	public void setKills(int kills) {
		this.kills = kills;
		if (owner.isOnline())
			owner.update();
		Main.getVaroGame().getTopScores().update();
	}

	public void setLastEnemyContact(Date lastEnemyContact) {
		this.lastEnemyContact = lastEnemyContact;
	}

	public void setLastJoined(OffsetDateTime lastJoined) {
		this.lastJoined = lastJoined;
	}

	public void setLastLocation(Location lastLocation) {
		this.lastLocation = lastLocation;
	}

	public void setOwner(VaroPlayer owner) {
		this.owner = owner;
	}

	public void setRestoreBackup(InventoryBackup restoreBackup) {
		this.restoreBackup = restoreBackup;
	}

	public void setSessions(int sessions) {
		this.sessions = sessions;
	}

	public void setSessionsPlayed(int sessionsPlayed) {
		this.sessionsPlayed = sessionsPlayed;
	}

	public void setShowActionbar(boolean showActionbarTime) {
		this.showActionbar = showActionbarTime;
	}

	public void setShowScoreboard(boolean showScoreboard) {
		this.showScoreboard = showScoreboard;
	}

	public void setState(PlayerState state, boolean skipSpectator) {
		if (EventUtils.callEvent(new PlayerStateChangeEvent(owner, state))) return;

		this.state = state;

		switch (state) {
		case ALIVE:
			this.owner.setAlive();
			break;
		case DEAD:
			this.diedAt = new Date();
			break;
		case SPECTATOR:
		    if (!skipSpectator)
		        this.owner.setSpectacting();
			break;
		default:
			throw new Error("Unknown playerstate");
		}

		new WinnerCheck();
	}
	
	public void setState(PlayerState state) {
	    this.setState(state, false);
	}

	public void setTimeUntilAddSession(Date timeUntilNewSession) {
		this.timeUntilAddSession = timeUntilNewSession;
	}

	public void setWillClear(boolean willClear) {
		this.willClear = willClear;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public void setYoutubeLink(String youtubeLink) {
		this.youtubeLink = youtubeLink;
	}
}