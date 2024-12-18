package de.cuuky.varo.threads.daily.checks;

import java.util.Date;
import java.util.concurrent.TimeUnit;

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

public class NoJoinCheck extends Checker {

	@Override
	public void check() {
		int days = ConfigSetting.NO_ACTIVITY_DAYS.getValueAsInt();
		if (days < 0)
			return;

		for (VaroPlayer vp : VaroPlayer.getAlivePlayer()) {
			if (vp.getStats().getLastJoined() == null)
				vp.getStats().setLastJoined(new Date());

			Date current = new Date();

			if (vp.getStats().getLastJoined().before(DateUtils.addDays(current, -ConfigSetting.NO_ACTIVITY_DAYS.getValueAsInt()))) {
				new Alert(AlertType.NO_JOIN, vp.getName() + " hat die Anzahl an maximal inaktiven Tagen ueberschritten!");
				if (ConfigSetting.STRIKE_ON_NO_ACTIVITY.getValueAsBoolean()) {
					Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.ALERT, ConfigMessages.ALERT_NOT_JOIN_STRIKE.getValue(null, vp).replace("%days%", String.valueOf((int) getDateDiff(vp.getStats().getLastJoined(), current, TimeUnit.DAYS))).replace("%player%", vp.getName()), vp.getRealUUID());

					vp.getStats().addStrike(new Strike("Es wurde fuer zu viele Tage nicht auf den Server gejoint.", vp, "CONSOLE"));
				} else
					Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.ALERT, ConfigMessages.ALERT_NOT_JOIN.getValue(null, vp).replace("%days%", String.valueOf((int) getDateDiff(vp.getStats().getLastJoined(), current, TimeUnit.DAYS))).replace("%player%", vp.getName()), vp.getRealUUID());
			}
		}
	}
}