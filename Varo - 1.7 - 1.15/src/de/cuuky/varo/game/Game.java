package de.cuuky.varo.game;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.bot.BotLauncher;
import de.cuuky.varo.bot.discord.VaroDiscordBot;
import de.cuuky.varo.configuration.config.ConfigEntry;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.game.end.WinnerCheck;
import de.cuuky.varo.game.start.AutoStart;
import de.cuuky.varo.game.start.ProtectionTime;
import de.cuuky.varo.game.state.GameState;
import de.cuuky.varo.game.threads.VaroMainHeartbeatThread;
import de.cuuky.varo.game.threads.VaroStartThread;
import de.cuuky.varo.logger.logger.EventLogger;
import de.cuuky.varo.logger.logger.EventLogger.LogType;
import de.cuuky.varo.serialize.identifier.VaroSerializeField;
import de.cuuky.varo.serialize.identifier.VaroSerializeable;
import de.cuuky.varo.utils.VaroUtils;
import de.cuuky.varo.world.border.decrease.BorderDecreaseDayTimer;
import de.cuuky.varo.world.border.decrease.BorderDecreaseMinuteTimer;
import de.cuuky.varo.world.generators.SpawnGenerator;

public class Game implements VaroSerializeable {

	/*
	 * Partly OLD
	 */

	private static Game instance;

	@VaroSerializeField(path = "autostart")
	private AutoStart autostart;

	@VaroSerializeField(path = "borderDecrease")
	private BorderDecreaseDayTimer borderDecrease;

	@VaroSerializeField(path = "gamestate")
	private GameState gamestate;

	@VaroSerializeField(path = "lastCoordsPost")
	private Date lastCoordsPost;

	@VaroSerializeField(path = "lastDayTimer")
	private Date lastDayTimer;

	@VaroSerializeField(path = "lobby")
	private Location lobby;

	private BorderDecreaseMinuteTimer minuteTimer;
	private ProtectionTime protection;
	private int startCountdown, startScheduler;
	private boolean finaleJoinStart, firstTime;
	private VaroMainHeartbeatThread mainThread;

	public Game() { // Für Deserializer
		instance = this;
	}

	private void loadVariables() {
		startCountdown = ConfigEntry.STARTCOUNTDOWN.getValueAsInt();
		
		if(mainThread != null)
			mainThread.loadVariables();
	}
	
	public void start() {
		if(hasStarted() || isStarting())
			return;

		if(ConfigEntry.DO_RANDOMTEAM_AT_START.getValueAsInt() > 0) {
			VaroUtils.doRandomTeam(ConfigEntry.DO_RANDOMTEAM_AT_START.getValueAsInt());
			Bukkit.broadcastMessage(Main.getPrefix() + "Alle Spieler haben einen zufälligen Teampartner erhalten!");
		}

		if(ConfigEntry.DO_SPAWN_GENERATE_AT_START.getValueAsBoolean()) {
			new SpawnGenerator(VaroUtils.getMainWorld().getSpawnLocation(), (int) (VaroPlayer.getAlivePlayer().size() * 0.85), true, null, null);
			Bukkit.broadcastMessage(Main.getPrefix() + "Die Löcher für den Spawn wurden generiert!");
		}

		if(ConfigEntry.DO_SORT_AT_START.getValueAsBoolean()) {
			VaroUtils.sortPlayers();
			Bukkit.broadcastMessage(Main.getPrefix() + "Alle Spieler wurden sortiert!");
		}

		removeArentAtStart();
		if(minuteTimer != null)
			minuteTimer.remove();

		minuteTimer = new BorderDecreaseMinuteTimer();
		startScheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new VaroStartThread(), 0, 20);
	}
	
	public void abort() {
		Bukkit.getScheduler().cancelTask(startScheduler);
		Bukkit.broadcastMessage("§7Der Start wurde §cabgebrochen§7!");
		startCountdown = ConfigEntry.STARTCOUNTDOWN.getValueAsInt();
	}

	@SuppressWarnings("unchecked")
	private void removeArentAtStart() {
		if(!ConfigEntry.REMOVE_PLAYERS_ARENT_AT_START.getValueAsBoolean())
			return;

		for(VaroPlayer varoplayer : (ArrayList<VaroPlayer>) VaroPlayer.getVaroPlayer().clone())
			if(!varoplayer.isOnline())
				varoplayer.delete();
	}
	
	public void end(WinnerCheck check) {
		this.gamestate = GameState.END;

		for(VaroPlayer vp : check.getPlaces().get(1)) {
			if(!vp.isOnline())
				continue;

			Player p = vp.getPlayer();
			p.getWorld().spawnEntity(p.getLocation().clone().add(1, 0, 0), EntityType.FIREWORK);
			p.getWorld().spawnEntity(p.getLocation().clone().add(-1, 0, 0), EntityType.FIREWORK);
			p.getWorld().spawnEntity(p.getLocation().clone().add(0, 0, 1), EntityType.FIREWORK);
			p.getWorld().spawnEntity(p.getLocation().clone().add(0, 0, -1), EntityType.FIREWORK);
		}

		String first = "";
		String second = "";
		String third = "";
		for(int i = 1; i <= 3; i++) {
			ArrayList<VaroPlayer> won;
			won = check.getPlaces().get(i);

			if(won == null)
				break;

			String names = "";
			for(VaroPlayer vp : won)
				names = names + (!won.toArray()[won.size() - 1].equals(vp) ? vp.getName() + (won.size() > 2 ? (won.toArray()[won.size() - 2].equals(vp) ? "" : ", ") : "") : ((won.size() == 1 ? "" : " & ") + vp.getName()));
			names = names + (won.get(0).getTeam() != null ? " (#" + won.get(0).getTeam().getName() + ")" : "");

			switch(i) {
			case 1:
				first = names;
				break;
			case 2:
				second = names;
				break;
			case 3:
				third = names;
				break;
			}
		}

		Bukkit.broadcastMessage(Main.getColorCode() + first + " §7" + (first.contains("&") ? "haben" : "hat") + " das Projekt für sich entschieden! §5Herzlichen Glückwunsch!");
		EventLogger.getInstance().println(LogType.WIN, first + " " + (first.contains("&") ? "haben" : "hat") + " das Projekt für sich entschieden! Herzlichen Glückwunsch!");
		VaroDiscordBot db = BotLauncher.getDiscordBot();
		if(db != null && db.isEnabled()) {
			if(db.getResultChannel() != null && db.isEnabled())
				db.sendMessage((":first_place: " + first + (second != null ? "\n" + ":second_place: " + second : "") + (third != null ? "\n" + ":third_place: " + third : "")) + "\n\nHerzlichen Glückwunsch!", "Das Projekt ist nun vorbei!", Color.MAGENTA, BotLauncher.getDiscordBot().getResultChannel());

			File file = new File("plugins/Varo/logs", "logs.yml");
			if(file.exists())
				db.sendFile("Die Logs des Projektes", file, BotLauncher.getDiscordBot().getResultChannel());
		}
	}

	private void startRefreshTimer() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), mainThread = new VaroMainHeartbeatThread(), 0, 20);
	}
	
	public VaroMainHeartbeatThread getMainThread() {
		return this.mainThread;
	}

	public AutoStart getAutoStart() {
		return autostart;
	}

	public boolean getFinaleJoinStart() {
		return finaleJoinStart;
	}

	public GameState getGameState() {
		return gamestate;
	}

	public Date getLastCoordsPost() {
		return lastCoordsPost;
	}

	public Date getLastDayTimer() {
		return lastDayTimer;
	}

	public Location getLobby() {
		return lobby;
	}

	public ProtectionTime getProtection() {
		return protection;
	}

	public int getStartCountdown() {
		return startCountdown;
	}

	public boolean hasStarted() {
		return gamestate != GameState.LOBBY;
	}

	public boolean isFirstTime() {
		return firstTime;
	}

	public boolean isRunning() {
		return gamestate == GameState.STARTED;
	}

	public boolean isStarting() {
		return startCountdown != ConfigEntry.STARTCOUNTDOWN.getValueAsInt();
	}

	public void setAutoStart(AutoStart autoStart) {
		this.autostart = autoStart;
	}

	public void setBorderDecrease(BorderDecreaseDayTimer borderDecrease) {
		this.borderDecrease = borderDecrease;
	}

	public void setFinaleJoinStart(boolean finaleJoinStart) {
		this.finaleJoinStart = finaleJoinStart;
	}

	public void setGamestate(GameState gamestate) {
		this.gamestate = gamestate;
	}

	public void setLastCoordsPost(Date lastCoordsPost) {
		this.lastCoordsPost = lastCoordsPost;
	}

	public void setLastDayTimer(Date lastDayTimer) {
		this.lastDayTimer = lastDayTimer;
	}

	public void setLobby(Location lobby) {
		this.lobby = lobby;
	}

	public void setProtection(ProtectionTime protection) {
		this.protection = protection;
	}
	
	public void setFirstTime(boolean firstTime) {
		this.firstTime = firstTime;
	}
	
	public int getStartScheduler() {
		return this.startScheduler;
	}
	
	@Override
	public void onDeserializeEnd() {
		startRefreshTimer();

		loadVariables();

		if(gamestate == GameState.STARTED)
			minuteTimer = new BorderDecreaseMinuteTimer();
	}

	@Override
	public void onSerializeStart() {}

	public static Game getInstance() {
		return instance;
	}

	public static void initialize() {
		instance = new Game();

		instance.startRefreshTimer();
		instance.loadVariables();

		instance.gamestate = GameState.LOBBY;
		instance.borderDecrease = new BorderDecreaseDayTimer(true);
	}
}