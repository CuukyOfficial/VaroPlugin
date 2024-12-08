package de.cuuky.varo.threads.daily.checks;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.time.DateUtils;

import de.cuuky.varo.alert.AlertType;
import de.cuuky.varo.config.language.Messages;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.player.stats.stat.Strike;
import de.cuuky.varo.threads.daily.Checker;
import io.github.almightysatan.slams.Placeholder;

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
				if (ConfigSetting.STRIKE_ON_NO_ACTIVITY.getValueAsBoolean()) {
					Messages.ALERT_STRIKE_NOT_JOIN.alert(AlertType.NO_JOIN, vp, Placeholder.constant("no-join-days", String.valueOf((int) getDateDiff(vp.getStats().getLastJoined(), current, TimeUnit.DAYS))));
					
					vp.getStats().addStrike(new Strike("Es wurde fuer zu viele Tage nicht auf den Server gejoint.", vp, "CONSOLE"));
				} else
				    Messages.ALERT_NOT_JOIN.alert(AlertType.NO_JOIN, vp, Placeholder.constant("no-join-days", String.valueOf((int) getDateDiff(vp.getStats().getLastJoined(), current, TimeUnit.DAYS))));
			}
		}
	}
}