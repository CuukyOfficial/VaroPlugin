package de.cuuky.varo.combatlog;

import de.cuuky.varo.logger.logger.EventLogger;
import org.bukkit.Bukkit;

import de.cuuky.varo.alert.Alert;
import de.cuuky.varo.alert.AlertType;
import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.config.messages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.event.BukkitEventType;
import de.cuuky.varo.entity.player.stats.stat.Strike;
import de.cuuky.varo.logger.logger.EventLogger.LogType;

public class Combatlog {

	/*
	 * OLD CODE
	 */

	public Combatlog(VaroPlayer player) {
		player.onEvent(BukkitEventType.KICKED);
		new Alert(AlertType.COMBATLOG, player.getName() + " hat sich im Kampf ausgeloggt!");
		if(ConfigEntry.STRIKE_ON_COMBATLOG.getValueAsBoolean()) {
			player.getStats().addStrike(new Strike("Es wurde sich während des Kampfes ausgeloggt.", player, "CONSOLE"));
			EventLogger.getInstance().println(LogType.ALERT, ConfigMessages.ALERT_COMBAT_LOG_STRIKE.getValue(player));
		} else
			EventLogger.getInstance().println(LogType.ALERT, ConfigMessages.ALERT_COMBAT_LOG.getValue(player));

		Bukkit.broadcastMessage(ConfigMessages.COMBAT_LOGGED_OUT.getValue(player));
	}
}
