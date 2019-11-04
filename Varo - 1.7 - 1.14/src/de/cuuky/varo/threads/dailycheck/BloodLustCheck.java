package de.cuuky.varo.threads.dailycheck;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import de.cuuky.varo.Main;
import de.cuuky.varo.alert.Alert;
import de.cuuky.varo.alert.AlertType;
import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.config.messages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.stats.stat.Strike;
import de.cuuky.varo.logger.logger.EventLogger.LogType;

public class BloodLustCheck extends Checker {

	@Override
	public void check() {
		int days = ConfigEntry.BLOODLUST_DAYS.getValueAsInt();
		boolean strike = ConfigEntry.STRIKE_ON_BLOODLUST.getValueAsBoolean();
		if(!ConfigEntry.BLOODLUST_DAYS.isIntActivated())
			return;

		for(VaroPlayer player : VaroPlayer.getAlivePlayer()) {
			Date lastContact = player.getStats().getLastEnemyContact();

			if(lastContact.before(DateUtils.addDays(new Date(), -days))) {
				new Alert(AlertType.BLOODLUST, player.getName() + " hat nun " + days + " Tage nicht gekämpft!");
				if(strike) {
					player.getStats().addStrike(new Strike("Es wurde für zu viele Tage nicht gekämpft.", player, "CONSOLE"));
					Main.getLoggerMaster().getEventLogger().println(LogType.ALERT, ConfigMessages.ALERT_NO_BLOODLUST_STRIKE.getValue(player).replace("%days%", String.valueOf(days)));
				} else
					Main.getLoggerMaster().getEventLogger().println(LogType.ALERT, ConfigMessages.ALERT_NO_BLOODLUST.getValue(player).replace("%days%", String.valueOf(days)));
			}
		}
	}
}