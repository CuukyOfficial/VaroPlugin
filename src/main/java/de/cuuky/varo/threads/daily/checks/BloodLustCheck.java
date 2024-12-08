package de.cuuky.varo.threads.daily.checks;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import de.cuuky.varo.alert.AlertType;
import de.cuuky.varo.config.language.Messages;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.player.stats.stat.Strike;
import de.cuuky.varo.threads.daily.Checker;
import io.github.almightysatan.slams.Placeholder;

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
					player.getStats().addStrike(new Strike("Es wurde fuer zu viele Tage nicht gekaempft.", player, "CONSOLE"));
					Messages.ALERT_STRIKE_NO_BLOODLUST.alert(AlertType.BLOODLUST, player, Placeholder.constant("bloodlust-days", String.valueOf(days)));
				} else
				    Messages.ALERT_NO_BLOODLUST.alert(AlertType.BLOODLUST, player, Placeholder.constant("bloodlust-days", String.valueOf(days)));
			}
		}
	}
}