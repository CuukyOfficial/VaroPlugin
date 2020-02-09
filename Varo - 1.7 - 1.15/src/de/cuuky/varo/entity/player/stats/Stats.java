package de.cuuky.varo.entity.player.stats;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.time.DateUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.Main;
import de.cuuky.varo.alert.Alert;
import de.cuuky.varo.alert.AlertType;
import de.cuuky.varo.api.VaroAPI;
import de.cuuky.varo.api.event.events.player.PlayerStateChangeEvent;
import de.cuuky.varo.api.event.events.player.strike.PlayerStrikeReceiveEvent;
import de.cuuky.varo.api.event.events.player.strike.PlayerStrikeRemoveEvent;
import de.cuuky.varo.configuration.config.ConfigEntry;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.stats.stat.PlayerState;
import de.cuuky.varo.entity.player.stats.stat.Strike;
import de.cuuky.varo.entity.player.stats.stat.YouTubeVideo;
import de.cuuky.varo.entity.player.stats.stat.inventory.InventoryBackup;
import de.cuuky.varo.entity.player.stats.stat.inventory.VaroSaveable;
import de.cuuky.varo.event.VaroEvent;
import de.cuuky.varo.game.VaroGame;
import de.cuuky.varo.game.end.WinnerCheck;
import de.cuuky.varo.logger.logger.EventLogger;
import de.cuuky.varo.logger.logger.EventLogger.LogType;
import de.cuuky.varo.scoreboard.ScoreboardHandler;
import de.cuuky.varo.serialize.identifier.VaroSerializeField;
import de.cuuky.varo.serialize.identifier.VaroSerializeable;
import de.cuuky.varo.threads.OutSideTimeChecker;
import de.cuuky.varo.utils.VaroUtils;
import de.cuuky.varo.version.VersionUtils;

public class Stats implements VaroSerializeable {

	@VaroSerializeField(path = "state")
	private PlayerState state;
	@VaroSerializeField(path = "lastLocation")
	private Location lastLocation;
	
	@VaroSerializeField(path = "countdown")
	private int countdown;
	@VaroSerializeField(path = "kills")
	private int kills;
	@VaroSerializeField(path = "sessions")
	private int sessions;
	@VaroSerializeField(path = "sessionsPlayed")
	private int sessionsPlayed;
	@VaroSerializeField(path = "wins")
	private int wins;
	
	@VaroSerializeField(path = "showActionbarTime")
	private boolean showActionbarTime;
	@VaroSerializeField(path = "showScoreboard")
	private boolean showScoreboard;
	@VaroSerializeField(path = "willClear")
	private boolean willClear;
	
	@VaroSerializeField(path = "firstTimeJoined")
	private Date firstTimeJoined;
	@VaroSerializeField(path = "lastJoined")
	private Date lastJoined;
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
	@VaroSerializeField(path = "strikes", arrayClass = Strike.class)
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
		owner.update();
		ScoreboardHandler.getInstance().updateTopScores();
	}

	public void addSaveable(VaroSaveable saveable) {
		saveables.add(saveable);
	}

	public void addSessionPlayed() {
		sessionsPlayed++;
	}

	public void addStrike(Strike strike) {
		if(VaroAPI.getEventManager().executeEvent(new PlayerStrikeReceiveEvent(strike)))
			return;

		this.strikes.add(strike);
		strike.activate(strikes.size());

		new Alert(AlertType.STRIKE, this.owner.getName() + " hat einen Strike erhalten! " + this.owner.getName() + " hat jetzt " + strikes.size());
	}

	public void addVideo(YouTubeVideo video) {
		videos.add(video);

		EventLogger.getInstance().println(LogType.YOUTUBE, owner.getName() + " hat heute folgendes Projektvideo hochgeladen: " + video.getLink());
	}

	public void addWin() {
		wins++;
	}

	public void clearInventory() {
		if(owner.isOnline()) {
			owner.getPlayer().getInventory().clear();
			for(ItemStack stack : owner.getPlayer().getInventory().getArmorContents())
				stack.setType(Material.AIR);
		} else
			setWillClear(true);

		new Alert(AlertType.INVENTORY_CLEAR, this.owner.getName() + "'s Inventar wurde geleert!");
	}

	public int getCountdown() {
		return this.countdown;
	}

	public String getCountdownMin(int sec) {
		int min = sec / 60;

		if(min < 10)
			return "0" + min;
		else
			return min + "";
	}

	public String getCountdownSec(int sec) {
		sec = sec % 60;

		if(sec < 10)
			return "0" + sec;
		else
			return sec + "";
	}

	public Date getDiedAt() {
		return diedAt;
	}

	public Date getFirstTimeJoined() {
		return firstTimeJoined;
	}

	public ArrayList<InventoryBackup> getInventoryBackups() {
		return inventoryBackups;
	}

	public KickResult getKickResult(Player player) {
		KickResult result = KickResult.ALLOW;
		if(VaroGame.getInstance().hasStarted()) {
			if(owner.isRegistered())
				result = getVaroKickResult();
			else
				result = KickResult.NO_PROJECTUSER;
		} else {
			if(!ConfigEntry.UNREGISTERED_PLAYER_JOIN.getValueAsBoolean() && !owner.isRegistered())
				result = KickResult.NO_PROJECTUSER;

			if(VaroGame.getInstance().isStarting())
				result = KickResult.NO_PROJECTUSER;
		}

		if(Bukkit.hasWhitelist() && !Bukkit.getWhitelistedPlayers().contains(player))
			result = KickResult.SERVER_NOT_PUBLISHED;

		if(player.isBanned())
			result = KickResult.BANNED;

		if(VersionUtils.getOnlinePlayer().size() >= Bukkit.getMaxPlayers())
			result = KickResult.SERVER_FULL;

		if(result != KickResult.ALLOW && result != KickResult.MASS_RECORDING_JOIN && result != KickResult.SPECTATOR && result != KickResult.FINALE_JOIN)
			if(player.hasPermission("varo.alwaysjoin") && ConfigEntry.IGNORE_JOINSYSTEMS_AS_OP.getValueAsBoolean() || !VaroGame.getInstance().hasStarted() && player.isOp()) {
				if(VaroGame.getInstance().hasStarted())
					if(result == KickResult.DEAD || !owner.isRegistered())
						setState(PlayerState.SPECTATOR);
					else
						owner.setAdminIgnore(true);

				result = KickResult.ALLOW;
			}

		return result;
	}

	public int getKills() {
		return kills;
	}

	public Date getLastEnemyContact() {
		return lastEnemyContact;
	}

	public Date getLastJoined() {
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
		if(owner.getTeam() != null)
			return owner.getTeam().getSaveables();
		else
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

		return new String[] { "§7ID§8: " + colorcode + owner.getId(), "§7UUID§8: " + colorcode + owner.getUuid(), "§7Team§8: " + colorcode + (owner.getTeam() != null ? owner.getTeam().getDisplay() : "/"), "§7Rank§8: " + colorcode + (owner.getRank() != null ? owner.getRank().getDisplay() : "/"), "§7Sessions§8: " + colorcode + sessions, "§7Sessions Played§8: " + colorcode + sessionsPlayed, "§7Countdown§8: " + colorcode + countdown, "§7Kills§8: " + colorcode + kills, "§7WillClearInventory§8: " + colorcode + willClear, "§7ShowScoreboard§8: " + colorcode + showScoreboard, "§7LastLocation§8: " + colorcode + (lastLocation != null ? VaroUtils.formatLocation(lastLocation, colorcode + "x§7, " + colorcode + "y§7, " + colorcode + "z§7 in " + colorcode + "world") : "/"), "§7TimeUntilAddSession§8: " + colorcode + (timeUntilAddSession != null ? dateFormat.format(timeUntilAddSession.getTime()) : "/"), "§7FirstTimeJoined§8: " + colorcode + (firstTimeJoined != null ? dateFormat.format(firstTimeJoined) : "/"), "§7LastTimeJoined§8: " + colorcode + (lastJoined != null ? dateFormat.format(lastJoined) : "/"), "§7LastEnemyContact§8: " + colorcode + (lastEnemyContact != null ? dateFormat.format(lastEnemyContact) : "/"), "§7DiedAt§8: " + colorcode + (diedAt == null ? "/" : dateFormat.format(diedAt)), "§7YouTubeLink§8: " + colorcode + (youtubeLink != null ? youtubeLink : "/"), "§7YouTubeVideos§8: " + colorcode + (videos == null ? 0 : videos.size()), "§7StrikeAmount§8: " + colorcode + (strikes == null ? 0 : strikes.size()), "§7State§8: " + colorcode + state.getName() };
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
		if(this.sessions > 0) {
			result = KickResult.ALLOW;
		} else {
			if(ConfigEntry.SESSIONS_PER_DAY.getValueAsInt() > 0) {
				if(ConfigEntry.PRE_PRODUCE_SESSIONS.getValueAsInt() > 0) {
					result = KickResult.NO_PREPRODUCES_LEFT;
				} else {
					result = KickResult.NO_SESSIONS_LEFT;
				}
			} else {
				result = KickResult.NO_TIME;
			}
		}

		if(VaroEvent.getMassRecEvent().isEnabled())
			result = KickResult.MASS_RECORDING_JOIN;

		if(VaroGame.getInstance().getFinaleJoinStart()) {
			result = KickResult.FINALE_JOIN;
		}

		if(Main.isBootedUp())
			if(!OutSideTimeChecker.getInstance().canJoin())
				result = KickResult.NOT_IN_TIME;

		for(Strike strike : strikes)
			if(strike.getBanUntil() != null)
				if(curr.before(strike.getBanUntil()))
					result = KickResult.STRIKE_BAN;

		if(!this.isAlive())
			result = KickResult.DEAD;

		if(isSpectator())
			result = KickResult.SPECTATOR;

		return result;
	}

	public ArrayList<YouTubeVideo> getVideos() {
		return videos;
	}

	public int getWins() {
		return wins;
	}

	public String getYoutubeLink() {
		return youtubeLink;
	}

	public boolean hasFullTime() {
		return countdown == ConfigEntry.PLAY_TIME.getValueAsInt() * 60;
	}

	public boolean hasTimeLeft() {
		return (this.countdown >= 1) && this.countdown != ConfigEntry.PLAY_TIME.getValueAsInt() * 60;
	}

	public boolean hasVideo(String videoId) {
		for(YouTubeVideo video : videos)
			if(video.getVideoId().equals(videoId))
				return true;

		return false;
	}

	public boolean isAlive() {
		if(this.state == PlayerState.ALIVE)
			return true;

		return false;
	}

	public boolean isShowActionbarTime() {
		return showActionbarTime;
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

		removeTeamAndRank();

		if(owner.isOnline()) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

				@Override
				public void run() {
					lastLocation = owner.getPlayer().getLocation();
				}
			}, 1);
		} else
			lastLocation = null;

	}

	public void loadStartDefaults() {
		if(owner.getTeam() != null)
			owner.getTeam().loadDefaults();

		videos = new ArrayList<YouTubeVideo>();
		strikes = new ArrayList<Strike>();
		saveables = new ArrayList<VaroSaveable>();
		inventoryBackups = new ArrayList<InventoryBackup>();
		playerBackpack = new VaroInventory(ConfigEntry.BACKPACK_PLAYER_SIZE.getValueAsInt());

		willClear = false;
		showScoreboard = true;
		diedAt = null;
		timeUntilAddSession = null;

		firstTimeJoined = new Date();
		lastJoined = new Date();
		lastEnemyContact = new Date();
		if(ConfigEntry.SESSIONS_PER_DAY.getValueAsInt() > 0) {
			sessions = ConfigEntry.SESSIONS_PER_DAY.getValueAsInt();
		} else {
			sessions = 1;
		}
		if(ConfigEntry.PRE_PRODUCE_SESSIONS.getValueAsInt() > 0) {
			sessions += ConfigEntry.PRE_PRODUCE_SESSIONS.getValueAsInt();
		}
		sessionsPlayed = 0;
		countdown = ConfigEntry.PLAY_TIME.getValueAsInt() * 60;
	}

	@Override
	public void onDeserializeEnd() {}

	@Override
	public void onSerializeStart() {}

	public void remove() {
		if(videos != null)
			videos.forEach(video -> video.remove());

		if(saveables != null)
			saveables.forEach(saveable -> saveable.remove());

		if(inventoryBackups != null)
			inventoryBackups.forEach(b -> b.remove());

		setState(PlayerState.DEAD);
	}

	public void removeCountdown() {
		this.countdown = ConfigEntry.PLAY_TIME.getValueAsInt() * 60;
	}

	public void removeInventoryBackup(InventoryBackup backup) {
		backup.remove();
		inventoryBackups.remove(backup);
	}

	public void removeSaveable(VaroSaveable saveable) {
		saveables.remove(saveable);
	}

	public void removeStrike(Strike strike) {
		if(VaroAPI.getEventManager().executeEvent(new PlayerStrikeRemoveEvent(strike)))
			return;

		int strikeNumber = strike.getStrikeNumber();
		this.strikes.remove(strike);

		for(Strike aStrike : this.strikes) {
			if(aStrike.getStrikeNumber() > strikeNumber) {
				aStrike.decreaseStrikeNumber();
			}
		}
	}

	public void removeStrikes() {
		strikes.clear();
	}

	public void removeTeamAndRank() {
		if(owner.getTeam() != null)
			owner.getTeam().removeMember(owner);

		if(owner.getRank() != null)
			owner.setRank(null);
	}

	public void removeVideo(YouTubeVideo video) {
		videos.remove(video);
	}

	public void setBan() {
		sessions--;

		if(ConfigEntry.SESSIONS_PER_DAY.getValueAsInt() <= 0) {
			timeUntilAddSession = DateUtils.addHours(new Date(), ConfigEntry.JOIN_AFTER_HOURS.getValueAsInt());
		}
	}

	public void setCountdown(int time) {
		this.countdown = time;
	}

	public void setDiedAt(Date diedAt) {
		this.diedAt = diedAt;
	}

	public void setFirstTimeJoined(Date firstTimeJoined) {
		this.firstTimeJoined = firstTimeJoined;
	}

	public void setKills(int kills) {
		this.kills = kills;
		owner.update();
		ScoreboardHandler.getInstance().updateTopScores();
	}

	public void setLastEnemyContact(Date lastEnemyContact) {
		this.lastEnemyContact = lastEnemyContact;
	}

	public void setLastJoined(Date lastJoined) {
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

	public void setShowActionbarTime(boolean showActionbarTime) {
		this.showActionbarTime = showActionbarTime;
	}

	public void setShowScoreboard(boolean showScoreboard) {
		this.showScoreboard = showScoreboard;
	}

	public void setState(PlayerState state) {
		if(VaroAPI.getEventManager().executeEvent(new PlayerStateChangeEvent(owner, state)))
			return;

		this.state = state;
		if(state == PlayerState.DEAD)
			this.diedAt = new Date();

		new WinnerCheck();
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