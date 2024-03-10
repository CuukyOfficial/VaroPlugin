package de.cuuky.varo.entity.player.disconnect;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

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
import de.varoplugin.cfw.player.PlayerVersionAdapter;

public class VaroPlayerDisconnect {

	/*
	 * OLD CODE
	 */

	private static HashMap<UUID, VaroPlayerDisconnect> disconnects = new HashMap<>();
	private static HashMap<UUID, BukkitTask> scheds = new HashMap<>();

	private final UUID uuid;
	private int amount;
	private boolean kick;

	public VaroPlayerDisconnect(Player player) {
		this.uuid = player.getUniqueId();

		disconnects.put(this.uuid, this);
	}

	public void addDisconnect() {
	    if (this.kick) {
	        this.kick = false;
	        return;
	    }

	    VaroPlayer varoPlayer = VaroPlayer.getPlayer(uuid.toString());
	    if (varoPlayer != null) {
	        PlayerVersionAdapter versionAdapter = varoPlayer.getVersionAdapter();
	        if (versionAdapter != null && varoPlayer.getVersionAdapter().getPing() >= ConfigSetting.NO_DISCONNECT_PING.getValueAsInt())
	            return;
	    }

		if (playerIsDead())
			return;

		amount++;
	}

	public boolean check() {
		if (amount <= ConfigSetting.DISCONNECT_PER_SESSION.getValueAsInt())
			return false;

		VaroPlayer vp = VaroPlayer.getPlayer(uuid.toString());
		if (vp == null)
		    return false;

		vp.getStats().addSessionPlayed();
		vp.getStats().removeReamainingSession();
		if (vp.getStats().hasTimeLeft())
			vp.getStats().removeCountdown();

		if (ConfigSetting.STRIKE_ON_DISCONNECT.getValueAsBoolean())
			vp.getStats().addStrike(new Strike("Der Server wurde zu oft verlassen.", vp, "CONSOLE"));

		new Alert(AlertType.DISCONNECT, ConfigMessages.ALERT_DISCONNECT_TOO_OFTEN.getValue(null, vp));
		Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.ALERT, ConfigMessages.ALERT_DISCONNECT_TOO_OFTEN.getValue(null, vp));
		Main.getLanguageManager().broadcastMessage(ConfigMessages.QUIT_TOO_OFTEN, vp);
		this.remove();
		return true;
	}

	public int getDisconnects() {
		return this.amount;
	}

	public void setKick(boolean kick) {
        this.kick = kick;
    }

	public boolean playerIsDead() {
		Player player = Bukkit.getPlayer(uuid);
		if (player != null)
			if (!player.isDead() && player.getHealth() != 0)
				return false;

		return true;
	}

	public void remove() {
		disconnects.remove(this.uuid);
	}

	public static void disconnected(VaroPlayer vp) {
		if (!ConfigSetting.BAN_AFTER_DISCONNECT_MINUTES.isIntActivated())
			return;

		if (Main.getVaroGame().getGameState() != GameState.STARTED)
			return;

		if (!vp.getStats().isAlive())
			return;

		scheds.put(vp.getRealUUID(), new BukkitRunnable() {
			@Override
			public void run() {
				if (Main.getVaroGame().getGameState() != GameState.STARTED || vp.isOnline())
					return;

				vp.getStats().removeCountdown();
				vp.getStats().setState(PlayerState.DEAD);
				Main.getLanguageManager().broadcastMessage(ConfigMessages.QUIT_DISCONNECT_SESSION_END, vp).replace("%banTime%", String.valueOf(ConfigSetting.BAN_AFTER_DISCONNECT_MINUTES.getValueAsInt()));
			}
		}.runTaskLater(Main.getInstance(), (ConfigSetting.BAN_AFTER_DISCONNECT_MINUTES.getValueAsInt() * 60L) * 20));
	}

	public static VaroPlayerDisconnect getDisconnect(Player p) {
		return disconnects.get(p.getUniqueId());
	}

	public static void clearDisconnects() {
		disconnects.clear();
		for (BukkitTask task : scheds.values())
			task.cancel();
		scheds.clear();
	}

	public static void joinedAgain(Player player) {
	    VaroPlayerDisconnect disconnect = getDisconnect(player);
	    if (disconnect != null)
	        disconnect.setKick(false);
	    
		BukkitTask scheduler = scheds.remove(player.getUniqueId());
		if (scheduler != null)
		    scheduler.cancel();
	}
}