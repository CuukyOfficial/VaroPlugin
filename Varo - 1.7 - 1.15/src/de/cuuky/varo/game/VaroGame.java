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
import de.cuuky.varo.bot.discord.VaroDiscordBot;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.messages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.game.end.WinnerCheck;
import de.cuuky.varo.game.leaderboard.TopScoreList;
import de.cuuky.varo.game.start.AutoStart;
import de.cuuky.varo.game.start.ProtectionTime;
import de.cuuky.varo.game.state.GameState;
import de.cuuky.varo.game.threads.VaroMainHeartbeatThread;
import de.cuuky.varo.game.threads.VaroStartThread;
import de.cuuky.varo.game.world.VaroWorldHandler;
import de.cuuky.varo.game.world.border.decrease.BorderDecreaseDayTimer;
import de.cuuky.varo.game.world.border.decrease.BorderDecreaseMinuteTimer;
import de.cuuky.varo.game.world.generators.SpawnGenerator;
import de.cuuky.varo.logger.logger.EventLogger.LogType;
import de.cuuky.varo.serialize.identifier.VaroSerializeField;
import de.cuuky.varo.serialize.identifier.VaroSerializeable;
import de.cuuky.varo.spawns.sort.PlayerSort;
import de.cuuky.varo.utils.varo.VaroUtils;

public class VaroGame implements VaroSerializeable {

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

	private int startScheduler;
	private boolean finaleJoinStart, firstTime;
	private VaroMainHeartbeatThread mainThread;
	private VaroStartThread startThread;
	private BorderDecreaseMinuteTimer minuteTimer;
	private ProtectionTime protection;
	private VaroWorldHandler varoWorldHandler;
	private TopScoreList topScores;

	public VaroGame() {
		Main.setVaroGame(this);
	}

	private void loadVariables() {
		if(startThread != null)
			startThread.loadVaraibles();

		if(mainThread != null)
			mainThread.loadVariables();

		this.topScores = new TopScoreList();
	}

	public void init() {
		startRefreshTimer();
		loadVariables();

		this.varoWorldHandler = new VaroWorldHandler();

		this.gamestate = GameState.LOBBY;
		this.borderDecrease = new BorderDecreaseDayTimer(true);
	}

	public void start() {
		if(hasStarted() || isStarting())
			return;

		if(ConfigSetting.DO_RANDOMTEAM_AT_START.getValueAsInt() > 0) {
			VaroUtils.doRandomTeam(ConfigSetting.DO_RANDOMTEAM_AT_START.getValueAsInt());
			Bukkit.broadcastMessage(Main.getPrefix() + "Alle Spieler haben einen zufälligen Teampartner erhalten!");
		}

		if(ConfigSetting.DO_SPAWN_GENERATE_AT_START.getValueAsBoolean()) {
			new SpawnGenerator(Main.getVaroGame().getVaroWorldHandler().getMainWorld().getWorld().getSpawnLocation(), (int) (VaroPlayer.getAlivePlayer().size() * 0.85), true, null, null);
			Bukkit.broadcastMessage(Main.getPrefix() + "Die Löcher für den Spawn wurden generiert!");
		}

		if(ConfigSetting.DO_SORT_AT_START.getValueAsBoolean()) {
			new PlayerSort().sortPlayers();
			Bukkit.broadcastMessage(Main.getPrefix() + "Alle Spieler wurden sortiert!");
		}

		if(ConfigSetting.REMOVE_PLAYERS_ARENT_AT_START.getValueAsBoolean())
			removeArentAtStart();

		if(minuteTimer != null)
			minuteTimer.remove();

		startScheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), startThread = new VaroStartThread(), 0, 20);
	}

	public void abort() {
		Bukkit.getScheduler().cancelTask(startScheduler);
		Bukkit.broadcastMessage("§7Der Start wurde §cabgebrochen§7!");

		startThread = null;
	}

	@SuppressWarnings("unchecked")
	private void removeArentAtStart() {
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

		if(first.contains("&")) {
			Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.WIN, ConfigMessages.ALERT_WINNER_TEAM.getValue().replace("%winnerPlayers%", first));
			Bukkit.broadcastMessage(ConfigMessages.GAME_WIN_TEAM.getValue().replace("%winnerPlayers%", first));
		} else {
			Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.WIN, ConfigMessages.ALERT_WINNER.getValue().replace("%player%", first));
			Bukkit.broadcastMessage(ConfigMessages.GAME_WIN.getValue().replace("%player%", first));
		}

		VaroDiscordBot db = Main.getBotLauncher().getDiscordbot();
		if(db != null && db.isEnabled()) {
			if(db.getResultChannel() != null && db.isEnabled())
				db.sendMessage((":first_place: " + first + (second != null ? "\n" + ":second_place: " + second : "") + (third != null ? "\n" + ":third_place: " + third : "")) + "\n\nHerzlichen Glueckwunsch!", "Das Projekt ist nun vorbei!", Color.MAGENTA, Main.getBotLauncher().getDiscordbot().getResultChannel());

			if(Main.getBotLauncher().getDiscordbot().getResultChannel() != null) {
				File file = new File("plugins/Varo/logs", "logs.yml");
				if(file.exists())
					db.sendFile("Die Logs des Projektes", file, Main.getBotLauncher().getDiscordbot().getResultChannel());
			}
		}

		if(ConfigSetting.STOP_SERVER_ON_WIN.isIntActivated()) {
			Bukkit.getServer().broadcastMessage("§7Der Server wird in " + Main.getColorCode() + ConfigSetting.STOP_SERVER_ON_WIN.getValueAsInt() + " Sekunden §7heruntergefahren...");
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

				@Override
				public void run() {
					Bukkit.getServer().shutdown();
				}
			}, ConfigSetting.STOP_SERVER_ON_WIN.getValueAsInt() * 20);
		}
	}

	private void startRefreshTimer() {
//		mainThread = new VaroMainHeartbeatThread();
		
//		new Timer().schedule(new TimerTask() {
//			
//			@Override
//			public void run() {
//				System.out.println("REFRESH ");
//				mainThread.run();
//			}
//		}, 0, 1000);
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), mainThread = new VaroMainHeartbeatThread(), 0, 20);
	}

	public TopScoreList getTopScores() {
		return this.topScores;
	}

	public VaroMainHeartbeatThread getMainThread() {
		return this.mainThread;
	}

	public AutoStart getAutoStart() {
		return autostart;
	}

	public void setStartThread(VaroStartThread startThread) {
		this.startThread = startThread;
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
		return startThread != null;
	}

	public VaroWorldHandler getVaroWorldHandler() {
		return this.varoWorldHandler;
	}

	public void setAutoStart(AutoStart autoStart) {
		this.autostart = autoStart;
	}

	public void setBorderDecrease(BorderDecreaseDayTimer borderDecrease) {
		this.borderDecrease = borderDecrease;
	}

	public void setMinuteTimer(BorderDecreaseMinuteTimer minuteTimer) {
		this.minuteTimer = minuteTimer;
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
		if(gamestate == GameState.STARTED)
			minuteTimer = new BorderDecreaseMinuteTimer();

		startRefreshTimer();
		loadVariables();

		this.varoWorldHandler = new VaroWorldHandler();
	}

	@Override
	public void onSerializeStart() {}
}