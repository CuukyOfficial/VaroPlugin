package de.cuuky.varo.combatlog;

import org.bukkit.Bukkit;

import de.cuuky.varo.Main;
import de.cuuky.varo.alert.Alert;
import de.cuuky.varo.alert.AlertType;
import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.config.messages.ConfigMessages;
import de.cuuky.varo.logger.logger.EventLogger.LogType;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.player.event.BukkitEventType;
import de.cuuky.varo.player.stats.stat.Strike;

public class Combatlog {

	/*
	 * OLD CODE
	 */
	
	public Combatlog(VaroPlayer player) {
		player.onEvent(BukkitEventType.KICKED);
		new Alert(AlertType.COMBATLOG, player.getName() + " hat sich im Kampf ausgeloggt!");
		if(ConfigEntry.STRIKE_ON_COMBATLOG.getValueAsBoolean()) {
			player.getStats().addStrike(new Strike("CombatLog", player, "CONSOLE"));
			Main.getLoggerMaster().getEventLogger().println(LogType.ALERT, ConfigMessages.ALERT_COMBAT_LOG_STRIKE.getValue(player));
		} else
			Main.getLoggerMaster().getEventLogger().println(LogType.ALERT, ConfigMessages.ALERT_COMBAT_LOG.getValue(player));

		Bukkit.broadcastMessage(ConfigMessages.COMBAT_LOGGED_OUT.getValue(player));
	}
}
