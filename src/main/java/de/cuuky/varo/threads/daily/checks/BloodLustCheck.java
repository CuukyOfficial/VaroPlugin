package de.cuuky.varo.threads.daily.checks;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import de.cuuky.varo.Main;
import de.cuuky.varo.alert.Alert;
import de.cuuky.varo.alert.AlertType;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.logger.logger.EventLogger.LogType;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.player.stats.stat.Strike;
import de.cuuky.varo.threads.daily.Checker;

public class BloodLustCheck extends Checker {

	@Override
	public void check() {
		int days = ConfigSetting.BLOODLUST_DAYS.getValueAsInt();
		boolean strike = ConfigSetting.STRIKE_ON_BLOODLUST.getValueAsBoolean();
		if (!ConfigSetting.BLOODLUST_DAYS.isIntActivated())
			return;

		for (VaroPlayer player : VaroPlayer.getAlivePlayer()) {
			Date lastContact = player.getStats().getLastEnemyContact();

			if (lastContact.before(DateUtils.addDays(new Date(), -days))) {
				new Alert(AlertType.BLOODLUST, player.getName() + " hat nun " + days + " Tage nicht gekaempft!");
				if (strike) {
					player.getStats().addStrike(new Strike("Es wurde fuer zu viele Tage nicht gekaempft.", player, "CONSOLE"));
					Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.ALERT, ConfigMessages.ALERT_NO_BLOODLUST_STRIKE.getValue(null, player).replace("%days%", String.valueOf(days)), player.getRealUUID());
				} else
					Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.ALERT, ConfigMessages.ALERT_NO_BLOODLUST.getValue(null, player).replace("%days%", String.valueOf(days)), player.getRealUUID());
			}
		}
	}
}