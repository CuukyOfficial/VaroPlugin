package de.varoplugin.varo.tasks.checks;

import de.varoplugin.varo.alert.AlertType;
import de.varoplugin.varo.config.language.Messages;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;
import de.varoplugin.varo.player.VaroPlayer;
import de.varoplugin.varo.tasks.Task;
import io.github.almightysatan.slams.Placeholder;

import java.time.Duration;
import java.time.OffsetDateTime;

public class NoJoinCheck implements Task {

	@Override
	public void check() {
		int days = ConfigSetting.NO_ACTIVITY_DAYS.getValueAsInt();
		if (days < 0)
			return;

		for (VaroPlayer vp : VaroPlayer.getAlivePlayer()) {
			OffsetDateTime now =  OffsetDateTime.now();
			if (vp.getStats().getLastJoined() == null)
				vp.getStats().setLastJoined(now);

			if (vp.getStats().getLastJoined().isBefore(now.minusDays(ConfigSetting.NO_ACTIVITY_DAYS.getValueAsInt()))) {
				if (ConfigSetting.STRIKE_ON_NO_ACTIVITY.getValueAsBoolean()) {
					Messages.ALERT_STRIKE_NOT_JOIN.alert(AlertType.NO_JOIN, vp, Placeholder.constant("no-join-days", String.valueOf(Duration.between(vp.getStats().getLastJoined(), now).toDays())));
					
					vp.getStats().strike("Es wurde für zu viele Tage nicht auf den Server gejoint.", "CONSOLE");
				} else
				    Messages.ALERT_NOT_JOIN.alert(AlertType.NO_JOIN, vp, Placeholder.constant("no-join-days", String.valueOf(Duration.between(vp.getStats().getLastJoined(), now).toDays())));
			}
		}
	}
}