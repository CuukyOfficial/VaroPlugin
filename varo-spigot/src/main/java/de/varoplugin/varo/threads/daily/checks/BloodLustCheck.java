package de.varoplugin.varo.threads.daily.checks;

import de.varoplugin.varo.alert.AlertType;
import de.varoplugin.varo.config.language.Messages;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;
import de.varoplugin.varo.player.VaroPlayer;
import de.varoplugin.varo.threads.daily.Checker;
import io.github.almightysatan.slams.Placeholder;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

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
				if (strike) {
					player.getStats().strike("Es wurde für zu viele Tage nicht gekämpft.", "CONSOLE");
					Messages.ALERT_STRIKE_NO_BLOODLUST.alert(AlertType.BLOODLUST, player, Placeholder.constant("bloodlust-days", String.valueOf(days)));
				} else
				    Messages.ALERT_NO_BLOODLUST.alert(AlertType.BLOODLUST, player, Placeholder.constant("bloodlust-days", String.valueOf(days)));
			}
		}
	}
}