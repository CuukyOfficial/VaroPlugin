package de.cuuky.varo.threads.dailycheck;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.time.DateUtils;

import de.cuuky.varo.Main;
import de.cuuky.varo.alert.Alert;
import de.cuuky.varo.alert.AlertType;
import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.config.messages.ConfigMessages;
import de.cuuky.varo.logger.logger.EventLogger.LogType;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.player.stats.stat.Strike;

public class NoJoinCheck extends Checker {

	@Override
	public void check() {
		int days = ConfigEntry.NO_ACTIVITY_DAYS.getValueAsInt();
		if(days < 0)
			return;

		for(VaroPlayer vp : VaroPlayer.getAlivePlayer()) {
			if(vp.getStats().getLastJoined() == null)
				vp.getStats().setLastJoined(new Date());

			Date current = new Date();

			if(vp.getStats().getLastJoined().before(DateUtils.addDays(current, -ConfigEntry.NO_ACTIVITY_DAYS.getValueAsInt()))) {
				new Alert(AlertType.NO_JOIN, vp.getName() + " hat die Anzahl an maximal inaktiven Tagen überschritten!");
				if(ConfigEntry.STRIKE_ON_NO_ACTIVITY.getValueAsBoolean()) {
					Main.getLoggerMaster().getEventLogger().println(LogType.ALERT, ConfigMessages.ALERT_NOT_JOIN_STRIKE.getValue(vp).replace("%days%", String.valueOf((int) getDateDiff(vp.getStats().getLastJoined(), current, TimeUnit.DAYS))).replace("%player%", vp.getName()));

					vp.getStats().addStrike(new Strike("Activity Strike", vp, "CONSOLE"));
				} else
					Main.getLoggerMaster().getEventLogger().println(LogType.ALERT, ConfigMessages.ALERT_NOT_JOIN.getValue(vp).replace("%days%", String.valueOf((int) getDateDiff(vp.getStats().getLastJoined(), current, TimeUnit.DAYS))).replace("%player%", vp.getName()));
			}
		}
	}
}
