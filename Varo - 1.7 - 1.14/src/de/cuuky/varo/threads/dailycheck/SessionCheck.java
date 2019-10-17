package de.cuuky.varo.threads.dailycheck;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.config.messages.ConfigMessages;
import de.cuuky.varo.logger.logger.EventLogger.LogType;
import de.cuuky.varo.player.VaroPlayer;

public class SessionCheck extends Checker {

	@Override
	public void check() {
		int sessionsPerDay = ConfigEntry.SESSION_PER_DAY.getValueAsInt();
		int preProduceable = ConfigEntry.PRE_PRODUCE_AMOUNT.getValueAsInt();

		for(VaroPlayer vp : VaroPlayer.getVaroPlayer()) {
			if(sessionsPerDay > 0) {
				if(ConfigEntry.SESSION_PER_DAY_ADDSESSIONS.getValueAsBoolean())
					vp.getStats().setSessions(vp.getStats().getSessions() + sessionsPerDay);
				else
					vp.getStats().setSessions(sessionsPerDay);
			}

			if(preProduceable > 0) {
				if(vp.getStats().getPreProduced() > 0)
					vp.getStats().setPreProduced(vp.getStats().getPreProduced() - 1);

				if(vp.getStats().getPreProduced() <= 0)
					vp.getStats().setMaxProduced(false);
				else
					vp.getStats().setMaxProduced(true);
			}
		}

		if(sessionsPerDay > 0)
			Main.getLoggerMaster().getEventLogger().println(LogType.ALERT, ConfigMessages.ALERT_SESSION_RESET.getValue().replace("%amount%", String.valueOf(sessionsPerDay)));

		if(preProduceable > 0)
			Main.getLoggerMaster().getEventLogger().println(LogType.ALERT, ConfigMessages.ALERT_REMOVED_PRE_PRODUCED.getValue());
	}
}
