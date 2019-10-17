
package de.cuuky.varo.game;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.Main;
import de.cuuky.varo.api.VaroAPI;
import de.cuuky.varo.api.event.events.game.VaroStartEvent;
import de.cuuky.varo.bot.discord.VaroDiscordBot;
import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.config.messages.ConfigMessages;
import de.cuuky.varo.event.VaroEvent;
import de.cuuky.varo.game.end.WinnerCheck;
import de.cuuky.varo.game.start.AutoStart;
import de.cuuky.varo.game.start.ProtectionTime;
import de.cuuky.varo.game.state.GameState;
import de.cuuky.varo.logger.logger.EventLogger.LogType;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.player.event.BukkitEventType;
import de.cuuky.varo.player.stats.stat.PlayerState;
import de.cuuky.varo.serialize.identifier.VaroSerializeField;
import de.cuuky.varo.serialize.identifier.VaroSerializeable;
import de.cuuky.varo.utils.Utils;
import de.cuuky.varo.version.VersionUtils;
import de.cuuky.varo.version.types.Sounds;
import de.cuuky.varo.world.PlayerSort;
import de.cuuky.varo.world.border.BorderDecreaseDayTimer;
import de.cuuky.varo.world.border.BorderDecreaseMinuteTimer;

public class Game implements VaroSerializeable {

	/*
	 * Partly OLD
	 */

	@VaroSerializeField(path = "gamestate")
	private GameState gamestate;
	@VaroSerializeField(path = "autostart")
	private AutoStart autostart;
	@VaroSerializeField(path = "borderDecrease")
	private BorderDecreaseDayTimer borderDecrease;
	@VaroSerializeField(path = "setupNext")
	private boolean setupNext;
	@VaroSerializeField(path = "lobby")
	private Location lobby;
	@VaroSerializeField(path = "lastDayTimer")
	private Date lastDayTimer;
	@VaroSerializeField(path = "lastCoordsPost")
	private Date lastCoordsPost;

	private boolean showDistanceToBorder, showTimeInActionBar, firstTime = false;
	private int protectionTime, noKickDistance, playTime, startCountdown, startScheduler;
	private ProtectionTime protection;
	private BorderDecreaseMinuteTimer minuteTimer;

	public Game() {
		Main.setGame(this);
	}

	public Game(boolean main) {
		Main.setGame(this);

		startRefreshTimer();
		loadVariables();

		setupNext = false;
		gamestate = GameState.LOBBY;
		borderDecrease = new BorderDecreaseDayTimer(true);
	}

	private void startRefreshTimer() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {

			int seconds = 0;

			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				seconds++;
				if (gamestate == GameState.STARTED) {
					if (seconds == 60) {
						seconds = 0;
						if (ConfigEntry.KICK_AT_SERVER_CLOSE.getValueAsBoolean()) {
							double minutesToClose = (int) ((((long) Main.getDataManager().getTimeChecker().getDate2()
									.getTime().getTime() - new Date().getTime()) / 1000) / 60);

							if (minutesToClose == 10 || minutesToClose == 5 || minutesToClose == 3
									|| minutesToClose == 2 || minutesToClose == 1)
								Bukkit.broadcastMessage(ConfigMessages.KICK_SERVER_CLOSE_SOON.getValue()
										.replace("%minutes%", String.valueOf(minutesToClose)));

							if (!Main.getDataManager().getTimeChecker().canJoin())
								for (VaroPlayer vp : (ArrayList<VaroPlayer>) VaroPlayer.getOnlinePlayer().clone()) {
									vp.getStats().setCountdown(0);
									vp.getPlayer().kickPlayer(
											"§cDie Spielzeit ist nun vorüber!\n§7Versuche es morgen erneut");
								}
						}
					}

					if (ConfigEntry.PLAY_TIME.isIntActivated()) {
						for (VaroPlayer vp : (ArrayList<VaroPlayer>) VaroPlayer.getOnlinePlayer().clone()) {
							if (vp.getStats().isSpectator() || vp.isAdminIgnore())
								continue;

							int countdown = vp.getStats().getCountdown() - 1;
							Player p = vp.getPlayer();

							if (showTimeInActionBar || vp.getStats().isShowActionbarTime())
								vp.getNetworkManager()
										.sendActionbar(Main.getColorCode() + vp.getStats().getCountdownMin(countdown)
												+ "§8:" + Main.getColorCode()
												+ vp.getStats().getCountdownSec(countdown));
							else if (showDistanceToBorder) {
								int distance = (int) Main.getDataManager().getWorldHandler().getBorder()
										.getDistanceTo(p);
								if (!ConfigEntry.DISTANCE_TO_BORDER_REQUIRED.isIntActivated()
										|| distance <= ConfigEntry.DISTANCE_TO_BORDER_REQUIRED.getValueAsInt())
									vp.getNetworkManager()
											.sendActionbar("§7Distanz zur Border: " + Main.getColorCode() + distance);
							}

							if (countdown == playTime - protectionTime - 1 && !firstTime
									&& !VaroEvent.getMassRecEvent().isEnabled())
								Bukkit.broadcastMessage(ConfigMessages.JOIN_PROTECTION_OVER.getValue(vp));

							if (countdown == 30 || countdown == 10 || countdown == 5 || countdown == 4 || countdown == 3
									|| countdown == 2 || countdown == 1 || countdown == 0) {
								if (countdown == 0) {
									Bukkit.broadcastMessage(ConfigMessages.KICK_BROADCAST.getValue(vp));
									vp.onEvent(BukkitEventType.KICKED);
									p.kickPlayer(ConfigMessages.KICK_MESSAGE.getValue(vp));
									continue;
								} else {
									if (countdown == 1)
										if (!vp.canBeKicked(noKickDistance)) {
											vp.sendMessage(ConfigMessages.KICK_PLAYER_NEARBY.getValue().replace(
													"%distance%",
													String.valueOf(ConfigEntry.NO_KICK_DISTANCE.getValueAsInt())));
											countdown += 1;
										}

									Bukkit.broadcastMessage(ConfigMessages.KICK_IN_SECONDS.getValue()
											.replaceAll("%player%", vp.getName()).replaceAll("%countdown%",
													countdown == 1 ? "einer" : String.valueOf(countdown)));
								}
							}

							vp.getStats().setCountdown(countdown);
						}
					}
				}

				for (VaroPlayer vp : VaroPlayer.getOnlinePlayer()) {
					if (gamestate == GameState.LOBBY) {
						vp.getStats().setCountdown(playTime);
						if (vp.getStats().getState() == PlayerState.DEAD)
							vp.getStats().setState(PlayerState.ALIVE);
					}

					Main.getDataManager().getScoreboardHandler().update(vp);
					vp.getNetworkManager().sendTablist();
				}
			}
		}, 0, 20);
	}

	public void start() {
		if (isStarted() || isStarting())
			return;

		if (ConfigEntry.DO_RANDOMTEAM_AT_START.getValueAsBoolean())
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "varo randomteam 2");

		if (ConfigEntry.DO_SORT_AT_START.getValueAsBoolean())
			new PlayerSort();

		removeArentAtStart();
		if (minuteTimer != null)
			minuteTimer.remove();

		minuteTimer = new BorderDecreaseMinuteTimer();
		startScheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				if (VersionUtils.getOnlinePlayer().size() != 0)
					((Player) VersionUtils.getOnlinePlayer().toArray()[0]).getWorld().setTime(1000);

				if (startCountdown != 0)
					Bukkit.broadcastMessage(ConfigMessages.GAME_START_COUNTDOWN.getValue().replaceAll("%countdown%",
							startCountdown == 1 ? "einer" : String.valueOf(startCountdown)));

				if (startCountdown == ConfigEntry.STARTCOUNTDOWN.getValueAsInt() || startCountdown == 1) {
					for (VaroPlayer pl1 : VaroPlayer.getOnlinePlayer()) {
						if (pl1.getStats().isSpectator())
							continue;

						Player pl = pl1.getPlayer();
						pl.setGameMode(GameMode.ADVENTURE);
						pl1.cleanUpPlayer();
					}
				}

				if (startCountdown == 5 || startCountdown == 4 || startCountdown == 3 || startCountdown == 2
						|| startCountdown == 1) {
					for (VaroPlayer vp : VaroPlayer.getOnlinePlayer()) {
						if (vp.getStats().isSpectator())
							continue;

						Player pl = vp.getPlayer();
						pl.playSound(pl.getLocation(), Sounds.NOTE_BASS_DRUM.bukkitSound(), 1, 1);

						String[] title = ConfigMessages.GAME_VARO_START_TITLE.getValue()
								.replace("%countdown%", String.valueOf(startCountdown)).split("\n");
						if (title.length != 0)
							vp.getNetworkManager().sendTitle(title[0], title.length == 2 ? title[1] : "");
					}
				}

				if (startCountdown == 0) {
					for (VaroPlayer pl1 : VaroPlayer.getOnlinePlayer()) {
						if (pl1.getStats().isSpectator())
							continue;

						Player pl = pl1.getPlayer();
						pl.playSound(pl.getLocation(), Sounds.NOTE_PLING.bukkitSound(), 1, 1);
						pl.setGameMode(GameMode.SURVIVAL);
						pl1.cleanUpPlayer();
						pl1.getStats().loadStartDefaults();
					}

					if (VaroAPI.getEventManager().executeEvent(new VaroStartEvent(Game.this))) {
						startCountdown = ConfigEntry.STARTCOUNTDOWN.getValueAsInt();
						Bukkit.getScheduler().cancelTask(startScheduler);
						return;
					}

					setGamestate(GameState.STARTED);
					fillChests();
					Main.getDataManager().getWorldHandler().getWorld().strikeLightningEffect(
							Main.getDataManager().getWorldHandler().getWorld().getSpawnLocation());
					firstTime = true;
					Bukkit.broadcastMessage(ConfigMessages.GAME_VARO_START.getValue());
					Main.getLoggerMaster().getEventLogger().println(LogType.INFO,
							ConfigMessages.ALERT_GAME_STARTED.getValue());
					startCountdown = ConfigEntry.STARTCOUNTDOWN.getValueAsInt();
					Bukkit.getScheduler().cancelTask(startScheduler);

					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

						@Override
						public void run() {
							firstTime = false;
						}
					}, ConfigEntry.PLAY_TIME.getValueAsInt() * 60);

					Main.getDataManager().getItemHandler().getStartItems().giveToAll();
					if (ConfigEntry.STARTPERIOD_PROTECTIONTIME.getValueAsInt() > 0)
						protection = new ProtectionTime();

					return;
				}

				startCountdown--;
			}
		}, 0, 20);
	}

	public void setBorderDecrease(BorderDecreaseDayTimer borderDecrease) {
		this.borderDecrease = borderDecrease;
	}

	public void setLastCoordsPost(Date lastCoordsPost) {
		this.lastCoordsPost = lastCoordsPost;
	}

	public Date getLastCoordsPost() {
		return lastCoordsPost;
	}

	public Date getLastDayTimer() {
		return lastDayTimer;
	}

	public void setLastDayTimer(Date lastDayTimer) {
		this.lastDayTimer = lastDayTimer;
	}

	public void abort() {
		Bukkit.getScheduler().cancelTask(startScheduler);
		Bukkit.broadcastMessage("§7Der Start wurde §cabgebrochen§7!");
		startCountdown = ConfigEntry.STARTCOUNTDOWN.getValueAsInt();
	}

	public boolean isStarting() {
		return startCountdown != ConfigEntry.STARTCOUNTDOWN.getValueAsInt();
	}

	public void end(WinnerCheck check) {
		this.gamestate = GameState.END;

		for (VaroPlayer vp : check.getPlaces().get(1)) {
			if (!vp.isOnline())
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
		for (int i = 1; i <= 3; i++) {
			ArrayList<VaroPlayer> won;
			won = check.getPlaces().get(i);

			if (won == null)
				break;

			String names = "";
			for (VaroPlayer vp : won)
				names = names + (!won.toArray()[won.size() - 1].equals(vp)
						? vp.getName() + (won.size() > 2 ? (won.toArray()[won.size() - 2].equals(vp) ? "" : ", ") : "")
						: ((won.size() == 1 ? "" : " & ") + vp.getName()));
			names = names + (won.get(0).getTeam() != null ? " (#" + won.get(0).getTeam().getName() + ")" : "");

			switch (i) {
			case 1:
				first = names;
				break;
			case 2:
				second = names;
				break;
			case 3:
				third = names;
				break;
			default:
				break;
			}
		}

		Bukkit.broadcastMessage(Main.getColorCode() + first + " §7" + (first.contains("&") ? "haben" : "hat")
				+ " das Projekt für sich entschieden! §5Herzlichen Glückwunsch!");
		Main.getLoggerMaster().getEventLogger().println(LogType.WIN,
				first + " " + (first.contains("&") ? "haben" : "hat")
						+ " das Projekt für sich entschieden! Herzlichen Glückwunsch!");
		VaroDiscordBot db = Main.getDiscordBot();
		if (db != null && db.isEnabled()) {
			if (db.getResultChannel() != null && db.isEnabled())
				db.sendMessage(
						(":first_place: " + first + (second != null ? "\n" + ":second_place: " + second : "")
								+ (third != null ? "\n" + ":third_place: " + third : ""))
								+ "\n\nHerzlichen Glückwunsch!",
						"Das Projekt ist nun vorbei!", Color.MAGENTA, Main.getDiscordBot().getResultChannel());

			File file = new File("plugins/Varo/logs", "logs.yml");
			if (file.exists())
				db.sendFile("Die Logs des Projektes", file, Main.getDiscordBot().getResultChannel());
		}
	}

	public ProtectionTime getProtection() {
		return protection;
	}

	public void setProtection(ProtectionTime protection) {
		this.protection = protection;
	}

	@SuppressWarnings("unchecked")
	private void removeArentAtStart() {
		if (!ConfigEntry.REMOVE_PLAYERS_ARENT_AT_START.getValueAsBoolean())
			return;

		for (VaroPlayer varoplayer : (ArrayList<VaroPlayer>) VaroPlayer.getVaroPlayer().clone())
			if (!varoplayer.isOnline())
				varoplayer.delete();
	}

	@SuppressWarnings("deprecation")
	private void fillChests() {
		if (!ConfigEntry.RANDOM_CHEST_FILL_RADIUS.isIntActivated())
			return;

		int radius = ConfigEntry.RANDOM_CHEST_FILL_RADIUS.getValueAsInt();
		Location loc = Main.getDataManager().getWorldHandler().getWorld().getSpawnLocation().clone().add(radius, radius,
				radius);
		Location loc2 = Main.getDataManager().getWorldHandler().getWorld().getSpawnLocation().clone().add(-radius,
				-radius, -radius);

		int itemsPerChest = ConfigEntry.RANDOM_CHEST_MAX_ITEMS_PER_CHEST.getValueAsInt();
		ArrayList<ItemStack> chestItems = Main.getDataManager().getItemHandler().getChestItems().getItems();
		for (Block block : getBlocksBetweenPoints(loc, loc2)) {
			if (!(block.getState() instanceof Chest))
				continue;

			Chest chest = (Chest) block.getState();
			chest.getBlockInventory().clear();
			for (int i = 0; i < itemsPerChest; i++) {
				int random = Utils.randomInt(0, chest.getBlockInventory().getSize() - 1);
				while (chest.getBlockInventory().getContents().length != chest.getBlockInventory().getSize())
					random = Utils.randomInt(0, chest.getBlockInventory().getSize() - 1);

				chest.getBlockInventory().setItem(random, chestItems.get(Utils.randomInt(0, chestItems.size() - 1)));
			}
		}

		Bukkit.broadcastMessage("§7Alle Kisten um den " + Main.getColorCode() + "Spawn §7wurden " + Main.getColorCode()
				+ "aufgefüllt§7!");
	}

	private List<Block> getBlocksBetweenPoints(Location l1, Location l2) {
		List<Block> blocks = new ArrayList<Block>();
		int topBlockX = (l1.getBlockX() < l2.getBlockX() ? l2.getBlockX() : l1.getBlockX());
		int bottomBlockX = (l1.getBlockX() > l2.getBlockX() ? l2.getBlockX() : l1.getBlockX());
		int topBlockY = (l1.getBlockY() < l2.getBlockY() ? l2.getBlockY() : l1.getBlockY());
		int bottomBlockY = (l1.getBlockY() > l2.getBlockY() ? l2.getBlockY() : l1.getBlockY());
		int topBlockZ = (l1.getBlockZ() < l2.getBlockZ() ? l2.getBlockZ() : l1.getBlockZ());
		int bottomBlockZ = (l1.getBlockZ() > l2.getBlockZ() ? l2.getBlockZ() : l1.getBlockZ());

		for (int x = bottomBlockX; x <= topBlockX; x++) {
			for (int y = bottomBlockY; y <= topBlockY; y++) {
				for (int z = bottomBlockZ; z <= topBlockZ; z++) {
					blocks.add(l1.getWorld().getBlockAt(x, y, z));
				}
			}
		}
		return blocks;
	}

	public boolean isFirstTime() {
		return firstTime;
	}

	public void loadVariables() {
		showDistanceToBorder = ConfigEntry.SHOW_DISTANCE_TO_BORDER.getValueAsBoolean();
		showTimeInActionBar = ConfigEntry.SHOW_TIME_IN_ACTIONBAR.getValueAsBoolean();
		protectionTime = ConfigEntry.JOIN_PROTECTIONTIME.getValueAsInt();
		noKickDistance = ConfigEntry.NO_KICK_DISTANCE.getValueAsInt();
		playTime = ConfigEntry.PLAY_TIME.getValueAsInt() * 60;
		startCountdown = ConfigEntry.STARTCOUNTDOWN.getValueAsInt();
	}

	public boolean isStarted() {
		return gamestate != GameState.LOBBY;
	}

	public void setGamestate(GameState gamestate) {
		this.gamestate = gamestate;
	}

	public int getStartCountdown() {
		return startCountdown;
	}

	public GameState getGameState() {
		return gamestate;
	}

	public AutoStart getAutoStart() {
		return autostart;
	}

	public void setAutoStart(AutoStart autoStart) {
		this.autostart = autoStart;
	}

	public void setWillSetupNext(boolean hasBeenExecuted) {
		setupNext = hasBeenExecuted;
	}

	public boolean willSetupNext() {
		return setupNext;
	}

	public Location getLobby() {
		return lobby;
	}

	public void setLobby(Location lobby) {
		this.lobby = lobby;
	}

	@Override
	public void onDeserializeEnd() {
		startRefreshTimer();

		loadVariables();

		if (gamestate == GameState.STARTED)
			minuteTimer = new BorderDecreaseMinuteTimer();
	}

	@Override
	public void onSerializeStart() {
	}
}
