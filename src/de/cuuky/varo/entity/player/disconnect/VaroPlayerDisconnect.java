package de.cuuky.varo.entity.player.disconnect;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.cuuky.cfw.version.VersionUtils;
import de.cuuky.varo.Main;
import de.cuuky.varo.alert.Alert;
import de.cuuky.varo.alert.AlertType;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.stats.stat.PlayerState;
import de.cuuky.varo.entity.player.stats.stat.Strike;
import de.cuuky.varo.game.state.GameState;
import de.cuuky.varo.logger.logger.EventLogger.LogType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class VaroPlayerDisconnect {

	/*
	 * OLD CODE
	 */

	private static ArrayList<VaroPlayerDisconnect> disconnects;
	private static HashMap<String, BukkitTask> scheds;

	static {
		disconnects = new ArrayList<>();
		scheds = new HashMap<>();
	}

	private int amount;
	private String name;

	public VaroPlayerDisconnect(Player player) {
		this.name = player.getName();

		disconnects.add(this);
	}

	public void addDisconnect() {
		if (VaroPlayer.getPlayer(name).getNetworkManager().getPing() >= ConfigSetting.NO_DISCONNECT_PING.getValueAsInt() || playerIsDead())
			return;

		amount++;
	}

	public boolean check() {
		if (amount <= ConfigSetting.DISCONNECT_PER_SESSION.getValueAsInt())
			return false;

		VaroPlayer vp = VaroPlayer.getPlayer(name);
		vp.getStats().setBan();
		if (vp.getStats().hasTimeLeft())
			vp.getStats().removeCountdown();

		if (ConfigSetting.STRIKE_ON_DISCONNECT.getValueAsBoolean())
			vp.getStats().addStrike(new Strike("Der Server wurde zu oft verlassen.", vp, "CONSOLE"));

		new Alert(AlertType.DISCONNECT, vp.getName() + " hat das Spiel zu oft verlassen! Seine Session wurde entfernt.");
		Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.ALERT, ConfigMessages.ALERT_DISCONNECT_TOO_OFTEN.getValue(null, vp));
		Main.getLanguageManager().broadcastMessage(ConfigMessages.QUIT_TOO_OFTEN, vp);
		this.remove();
		return true;
	}

	public int getDisconnects() {
		return this.amount;
	}

	public String getPlayer() {
		return this.name;
	}

	public boolean playerIsDead() {
		Player player = Bukkit.getPlayerExact(name);
		if (player != null)
			if (!player.isDead() && VersionUtils.getHearts(player) != 0)
				return false;

		return true;
	}

	public void remove() {
		disconnects.remove(this);
	}

	public static void disconnected(String playerName) {
		if (!ConfigSetting.BAN_AFTER_DISCONNECT_MINUTES.isIntActivated())
			return;

		if (Main.getVaroGame().getGameState() != GameState.STARTED)
			return;

		if (!VaroPlayer.getPlayer(playerName).getStats().isAlive())
			return;

		scheds.put(playerName, new BukkitRunnable() {
			@Override
			public void run() {
				if (Bukkit.getPlayerExact(playerName) != null)
					return;

				if (Main.getVaroGame().getGameState() != GameState.STARTED)
					return;

				VaroPlayer vp = VaroPlayer.getPlayer(playerName);
				vp.getStats().removeCountdown();
				vp.getStats().setState(PlayerState.DEAD);
				Main.getLanguageManager().broadcastMessage(ConfigMessages.QUIT_DISCONNECT_SESSION_END, vp).replace("%banTime%", String.valueOf(ConfigSetting.BAN_AFTER_DISCONNECT_MINUTES.getValueAsInt()));
			}
		}.runTaskLater(Main.getInstance(), (ConfigSetting.BAN_AFTER_DISCONNECT_MINUTES.getValueAsInt() * 60L) * 20));
	}

	public static VaroPlayerDisconnect getDisconnect(Player p) {
		for (VaroPlayerDisconnect disconnect : disconnects)
			if (disconnect.getPlayer().equals(p.getName()))
				return disconnect;

		return null;
	}

	public static void joinedAgain(String playerName) {
		if (!scheds.containsKey(playerName))
			return;

		scheds.get(playerName).cancel();
		scheds.remove(playerName);
	}
}