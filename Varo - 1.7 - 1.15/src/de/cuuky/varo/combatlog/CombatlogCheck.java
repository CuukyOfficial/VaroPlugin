package de.cuuky.varo.combatlog;

import org.bukkit.event.player.PlayerQuitEvent;

import de.cuuky.varo.Main;
import de.cuuky.varo.alert.Alert;
import de.cuuky.varo.alert.AlertType;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.event.BukkitEventType;
import de.cuuky.varo.entity.player.stats.stat.PlayerState;
import de.cuuky.varo.entity.player.stats.stat.Strike;
import de.cuuky.varo.game.state.GameState;
import de.cuuky.varo.logger.logger.EventLogger.LogType;

public class CombatlogCheck {

	/*
	 * OLD CODE
	 */

	private boolean combatLog;

	public CombatlogCheck(PlayerQuitEvent event) {
		this.combatLog = false;

		check(event);
	}

	private void check(PlayerQuitEvent event) {
		if (Main.getVaroGame().getGameState() == GameState.END || PlayerHit.getHit(event.getPlayer()) == null) {
			return;
		}

		VaroPlayer vp = VaroPlayer.getPlayer(event.getPlayer().getName());
		PlayerHit hit = PlayerHit.getHit(event.getPlayer());

		if (hit.getOpponent() != null && hit.getOpponent().isOnline())
			PlayerHit.getHit(hit.getOpponent()).over();

		if (!vp.getStats().isAlive()) {
			return;
		}

		if (ConfigSetting.KILL_ON_COMBATLOG.getValueAsBoolean()) {
			event.getPlayer().setHealth(0);
			vp.getStats().setState(PlayerState.DEAD);
		}

		this.combatLog = true;
		punish(vp);
	}

	private void punish(VaroPlayer player) {
		player.onEvent(BukkitEventType.KICKED);
		new Alert(AlertType.COMBATLOG, player.getName() + " hat sich im Kampf ausgeloggt!");
		if (ConfigSetting.STRIKE_ON_COMBATLOG.getValueAsBoolean()) {
			player.getStats().addStrike(new Strike("CombatLog", player, "CONSOLE"));
			Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.ALERT, ConfigMessages.ALERT_COMBAT_LOG_STRIKE.getValue(null, player));
		} else
			Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.ALERT, ConfigMessages.ALERT_COMBAT_LOG.getValue(null, player));

		Main.getLanguageManager().broadcastMessage(ConfigMessages.COMBAT_LOGGED_OUT, player);
	}

	public boolean isCombatLog() {
		return combatLog;
	}
}