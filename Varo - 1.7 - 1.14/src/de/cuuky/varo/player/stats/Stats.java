package de.cuuky.varo.player.stats;

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
import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.event.VaroEvent;
import de.cuuky.varo.game.end.WinnerCheck;
import de.cuuky.varo.logger.logger.EventLogger.LogType;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.player.stats.stat.PlayerState;
import de.cuuky.varo.player.stats.stat.Strike;
import de.cuuky.varo.player.stats.stat.YouTubeVideo;
import de.cuuky.varo.player.stats.stat.inventory.InventoryBackup;
import de.cuuky.varo.player.stats.stat.inventory.VaroSaveable;
import de.cuuky.varo.serialize.identifier.VaroSerializeField;
import de.cuuky.varo.serialize.identifier.VaroSerializeable;
import de.cuuky.varo.utils.LocationFormatter;
import de.cuuky.varo.version.VersionUtils;

public class Stats implements VaroSerializeable {

	@VaroSerializeField(path = "sessions")
	private int sessions;
	@VaroSerializeField(path = "preProduced")
	private int preProduced;
	@VaroSerializeField(path = "sessionsPlayed")
	private int sessionsPlayed;
	@VaroSerializeField(path = "countdown")
	private int countdown;
	@VaroSerializeField(path = "kills")
	private int kills;
	@VaroSerializeField(path = "wins")
	private int wins;

	@VaroSerializeField(path = "maxProduced")
	private boolean maxProduced;
	@VaroSerializeField(path = "willClear")
	private boolean willClear;
	@VaroSerializeField(path = "showScoreboard")
	private boolean showScoreboard;
	@VaroSerializeField(path = "showActionbarTime")
	private boolean showActionbarTime;

	@VaroSerializeField(path = "lastLocation")
	private Location lastLocation;
	@VaroSerializeField(path = "timeBanUntil")
	private Date timeBanUntil;
	@VaroSerializeField(path = "firstTimeJoined")
	private Date firstTimeJoined;
	@VaroSerializeField(path = "lastJoined")
	private Date lastJoined;
	@VaroSerializeField(path = "lastEnemyContact")
	private Date lastEnemyContact;
	@VaroSerializeField(path = "diedAt")
	private Date diedAt;
	@VaroSerializeField(path = "youtubeLink")
	private String youtubeLink;

	@VaroSerializeField(path = "strikes", arrayClass = Strike.class)
	private ArrayList<Strike> strikes;
	@VaroSerializeField(path = "saveables", arrayClass = VaroSaveable.class)
	private ArrayList<VaroSaveable> saveables;
	@VaroSerializeField(path = "videos", arrayClass = YouTubeVideo.class)
	private ArrayList<YouTubeVideo> videos;
	@VaroSerializeField(path = "inventoryBackups", arrayClass = InventoryBackup.class)
	private ArrayList<InventoryBackup> inventoryBackups;
	@VaroSerializeField(path = "restoreBackup")
	private InventoryBackup restoreBackup;
	@VaroSerializeField(path = "backpack")
	private VaroInventory backpack;
	@VaroSerializeField(path = "state")
	private PlayerState state;

	private VaroPlayer owner;

	public Stats() {}

	public Stats(VaroPlayer vp) {
		this.owner = vp;
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

	public void removeTeamAndRank() {
		if(owner.getTeam() != null)
			owner.getTeam().removeMember(owner);

		if(owner.getRank() != null)
			owner.setRank(null);
	}

	public void loadStartDefaults() {
		if(owner.getTeam() != null)
			owner.getTeam().loadDefaults();

		videos = new ArrayList<YouTubeVideo>();
		strikes = new ArrayList<Strike>();
		saveables = new ArrayList<VaroSaveable>();
		inventoryBackups = new ArrayList<InventoryBackup>();
		backpack = new VaroInventory(ConfigEntry.BACKPACK_SIZE.getValueAsInt());

		maxProduced = false;
		willClear = false;
		showScoreboard = true;
		diedAt = null;
		timeBanUntil = null;

		firstTimeJoined = new Date();
		lastJoined = new Date();
		lastEnemyContact = new Date();
		sessions = ConfigEntry.SESSION_PER_DAY.getValueAsInt();
		preProduced = 0;
		sessionsPlayed = 1;
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

	public VaroInventory getBackpack() {
		return backpack;
	}

	public int getKills() {
		return kills;
	}

	public void setKills(int kills) {
		this.kills = kills;
		owner.update();
		Main.getDataManager().getScoreboardHandler().updateTopScores();
	}

	public void addKill() {
		this.kills++;
		owner.update();
		Main.getDataManager().getScoreboardHandler().updateTopScores();
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

	public void addSaveable(VaroSaveable saveable) {
		saveables.add(saveable);
	}

	public void removeSaveable(VaroSaveable saveable) {
		saveables.remove(saveable);
	}

	public ArrayList<YouTubeVideo> getVideos() {
		return videos;
	}

	public void addVideo(YouTubeVideo video) {
		videos.add(video);

		Main.getLoggerMaster().getEventLogger().println(LogType.YOUTUBE, owner.getName() + " hat heute folgendes Projektvideo hochgeladen: " + video.getLink());
	}

	public void removeVideo(YouTubeVideo video) {
		videos.remove(video);
	}

	public boolean hasVideo(String videoId) {
		for(YouTubeVideo video : videos)
			if(video.getVideoId().equals(videoId))
				return true;

		return false;
	}

	public Location getLastLocation() {
		return lastLocation;
	}

	public Date getTimeBanUntil() {
		return timeBanUntil;
	}

	public void setTimeBanUntil(Date timeBanUntil) {
		this.timeBanUntil = timeBanUntil;
	}

	public void removeStrikes() {
		strikes.clear();
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public void addWin() {
		wins++;
	}

	public void setRestoreBackup(InventoryBackup restoreBackup) {
		this.restoreBackup = restoreBackup;
	}

	public InventoryBackup getRestoreBackup() {
		return restoreBackup;
	}

	public void setOwner(VaroPlayer owner) {
		this.owner = owner;
	}

	public String getName() {
		return owner.getName();
	}

	public void setLastLocation(Location lastLocation) {
		this.lastLocation = lastLocation;
	}

	public Date getLastJoined() {
		return lastJoined;
	}

	public void addStrike(Strike strike) {
		if(VaroAPI.getEventManager().executeEvent(new PlayerStrikeReceiveEvent(strike)))
			return;

		this.strikes.add(strike);
		strike.activate(strikes.size());

		new Alert(AlertType.STRIKE, this.owner.getName() + " hat einen Strike erhalten! " + this.owner.getName() + " hat jetzt " + strikes.size());
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

	public ArrayList<InventoryBackup> getInventoryBackups() {
		return inventoryBackups;
	}

	public void addInventoryBackup(InventoryBackup backup) {
		inventoryBackups.add(backup);
	}

	public void removeInventoryBackup(InventoryBackup backup) {
		backup.remove();
		inventoryBackups.remove(backup);
	}

	public boolean isShowScoreboard() {
		return showScoreboard;
	}

	public void setShowScoreboard(boolean showScoreboard) {
		this.showScoreboard = showScoreboard;
	}

	public void setLastJoined(Date lastJoined) {
		this.lastJoined = lastJoined;
	}

	public Date getLastEnemyContact() {
		return lastEnemyContact;
	}

	public void setLastEnemyContact(Date lastEnemyContact) {
		this.lastEnemyContact = lastEnemyContact;
	}

	public void setFirstTimeJoined(Date firstTimeJoined) {
		this.firstTimeJoined = firstTimeJoined;
	}

	public int getSessions() {
		return sessions;
	}

	public void setSessions(int sessions) {
		this.sessions = sessions;
	}

	public boolean isWillClear() {
		return willClear;
	}

	public void setWillClear(boolean willClear) {
		this.willClear = willClear;
	}

	public void setMaxProduced(boolean maxProduced) {
		this.maxProduced = maxProduced;
	}

	public int getPreProduced() {
		return preProduced;
	}

	public void setPreProduced(int preProduced) {
		this.preProduced = preProduced;
	}

	public boolean hasMaxProduced() {
		return preProduced >= ConfigEntry.PRE_PRODUCE_AMOUNT.getValueAsInt();
	}

	public boolean isMaxProduced() {
		return maxProduced;
	}

	public Date getFirstTimeJoined() {
		return firstTimeJoined;
	}

	public Date getDiedAt() {
		return diedAt;
	}

	public void setDiedAt(Date diedAt) {
		this.diedAt = diedAt;
	}

	public void removeStrike(Strike strike) {
		if(VaroAPI.getEventManager().executeEvent(new PlayerStrikeRemoveEvent(strike)))
			return;

		this.strikes.remove(strike);
	}

	public String getYoutubeLink() {
		return youtubeLink;
	}

	public void setYoutubeLink(String youtubeLink) {
		this.youtubeLink = youtubeLink;
	}

	public int getSessionsPlayed() {
		return sessionsPlayed;
	}

	public void addSessionPlayed() {
		sessionsPlayed++;
	}
	
	public void setSessionsPlayed(int sessionsPlayed) {
		this.sessionsPlayed = sessionsPlayed;
	}

	public PlayerState getState() {
		return state;
	}

	public boolean isSpectator() {
		return state == PlayerState.SPECTATOR;
	}

	public void setState(PlayerState state) {
		if(VaroAPI.getEventManager().executeEvent(new PlayerStateChangeEvent(owner, state)))
			return;

		this.state = state;
		if(state == PlayerState.DEAD)
			this.diedAt = new Date();

		new WinnerCheck();
	}

	public boolean isAlive() {
		if(this.state == PlayerState.ALIVE)
			return true;

		return false;
	}

	public ArrayList<Strike> getStrikes() {
		return strikes;
	}

	public void setShowActionbarTime(boolean showActionbarTime) {
		this.showActionbarTime = showActionbarTime;
	}

	public boolean isShowActionbarTime() {
		return showActionbarTime;
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

	public boolean hasTimeLeft() {
		return (this.countdown >= 1) && this.countdown != ConfigEntry.PLAY_TIME.getValueAsInt() * 60;
	}

	public boolean hasFullTime() {
		return countdown == ConfigEntry.PLAY_TIME.getValueAsInt() * 60;
	}

	public void setCountdown(int time) {
		this.countdown = time;
	}

	public void removeCountdown() {
		this.countdown = ConfigEntry.PLAY_TIME.getValueAsInt() * 60;
	}

	public void setBan() {
		if(ConfigEntry.SESSION_PER_DAY.isIntActivated())
			if(getSessions() > 0)
				sessions--;

		if(ConfigEntry.TIME_JOIN_HOURS.isIntActivated())
			timeBanUntil = DateUtils.addHours(new Date(), ConfigEntry.TIME_JOIN_HOURS.getValueAsInt());

		if(ConfigEntry.PRE_PRODUCE_AMOUNT.isIntActivated()) {
			preProduced++;
			if(hasMaxProduced())
				maxProduced = true;
		}
	}

	public KickResult getKickResult(Player player) {
		KickResult result = KickResult.ALLOW;
		if(Main.getGame().isStarted()) {
			if(owner.isRegistered())
				result = getVaroKickResult();
			else
				result = KickResult.NO_PROJECTUSER;
		} else {
			if(!ConfigEntry.UNREGISTERED_PLAYER_JOIN.getValueAsBoolean() && !owner.isRegistered())
				result = KickResult.NO_PROJECTUSER;

			if(Main.getGame().getStartCountdown() != ConfigEntry.STARTCOUNTDOWN.getValueAsInt())
				result = KickResult.NO_PROJECTUSER;
		}

		if(Bukkit.hasWhitelist() && !Bukkit.getWhitelistedPlayers().contains(player))
			result = KickResult.SERVER_NOT_PUBLISHED;

		if(player.isBanned())
			result = KickResult.BANNED;

		if(VersionUtils.getOnlinePlayer().size() >= Bukkit.getMaxPlayers())
			result = KickResult.SERVER_FULL;
		
		if(result != KickResult.ALLOW && result != KickResult.MASS_RECORDING_JOIN && result != KickResult.SPECTATOR)
			if(player.hasPermission("varo.alwaysjoin") && ConfigEntry.IGNORE_JOINSYSTEMS_AS_OP.getValueAsBoolean() || !Main.getGame().isStarted() && player.isOp()) {
				if(Main.getGame().isStarted())
					if(result == KickResult.DEAD || !owner.isRegistered())
						setState(PlayerState.SPECTATOR);
					else
						owner.setAdminIgnore(true);

				result = KickResult.ALLOW;
			}

		return result;
	}

	public KickResult getVaroKickResult() {
		GregorianCalendar curr = new GregorianCalendar();
		KickResult result = KickResult.ALLOW;
		if(ConfigEntry.SESSION_PER_DAY.isIntActivated())
			if(this.getSessions() <= 0)
				result = KickResult.NO_SESSIONS_LEFT;

		if(ConfigEntry.PRE_PRODUCE_AMOUNT.isIntActivated()) {
			if(ConfigEntry.BAN_AFTER_PREPRODUCE_DAY.getValueAsBoolean()) {
				if(isMaxProduced())
					result = KickResult.NO_PREPRODUCES_LEFT;
			} else {
				if(hasMaxProduced())
					result = KickResult.NO_PREPRODUCES_LEFT;
			}
		}
		
		if(ConfigEntry.TIME_JOIN_HOURS.isIntActivated())
			if(curr.before(getTimeBanUntil()))
				result = KickResult.NO_TIME;
		
		if(VaroEvent.getMassRecEvent().isEnabled())
			result = KickResult.MASS_RECORDING_JOIN;
		
		if(Main.isBootedUp())
			if(!Main.getDataManager().getTimeChecker().canJoin())
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

	public String[] getStatsListed() {
		String colorcode = Main.getColorCode();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		this.lastLocation = owner.isOnline() ? owner.getPlayer().getLocation() : lastLocation;

		return new String[] { "§7ID§8: " + colorcode + owner.getId(), "§7UUID§8: " + colorcode + owner.getUuid(), "§7Team§8: " + colorcode + (owner.getTeam() != null ? owner.getTeam().getDisplay() : "/"), "§7Rank§8: " + colorcode + (owner.getRank() != null ? owner.getRank().getDisplay() : "/"), "§7Sessions§8: " + colorcode + sessions, "§7PreProduced Sessions§8: " + colorcode + preProduced, "§7Sessions Played§8: " + colorcode + sessionsPlayed, "§7Countdown§8: " + colorcode + countdown, "§7Kills§8: " + colorcode + kills, "§7MaxProduced§8: " + colorcode + maxProduced, "§7WillClearInventory§8: " + colorcode + willClear, "§7ShowScoreboard§8: " + colorcode + showScoreboard, "§7LastLocation§8: " + colorcode + (lastLocation != null ? new LocationFormatter(colorcode + "x§7, " + colorcode + "y§7, " + colorcode + "z§7 in " + colorcode + "world").format(lastLocation) : "/"), "§7TimeBanUntil§8: " + colorcode + (timeBanUntil != null ? dateFormat.format(timeBanUntil.getTime()) : "/"), "§7FirstTimeJoined§8: " + colorcode + (firstTimeJoined != null ? dateFormat.format(firstTimeJoined) : "/"), "§7LastTimeJoined§8: " + colorcode + (lastJoined != null ? dateFormat.format(lastJoined) : "/"), "§7LastEnemyContact§8: " + colorcode + (lastEnemyContact != null ? dateFormat.format(lastEnemyContact) : "/"), "§7DiedAt§8: " + colorcode + (diedAt == null ? "/" : dateFormat.format(diedAt)), "§7YouTubeLink§8: " + colorcode + (youtubeLink != null ? youtubeLink : "/"), "§7YouTubeVideos§8: " + colorcode + (videos == null ? videos.size() : 0), "§7StrikeAmount§8: " + colorcode + (strikes == null ? strikes.size() : 0), "§7State§8: " + colorcode + state.getName() };
	}
}
