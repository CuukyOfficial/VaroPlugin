package de.cuuky.varo.threads.dailycheck;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.config.ConfigEntry;
import de.cuuky.varo.configuration.messages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.logger.logger.EventLogger.LogType;

public class SessionCheck extends Checker {

	@Override
	public void check() {
		if(ConfigEntry.SESSIONS_PER_DAY.getValueAsInt() <= 0) {
			return;
		}

		int normalSessions = ConfigEntry.SESSIONS_PER_DAY.getValueAsInt();
		int preProduceSessions;
		if(ConfigEntry.PRE_PRODUCE_SESSIONS.getValueAsInt() > 0) {
			preProduceSessions = ConfigEntry.PRE_PRODUCE_SESSIONS.getValueAsInt();
		} else {
			preProduceSessions = 0;
		}

		for(VaroPlayer vp : VaroPlayer.getVaroPlayer()) {

			vp.getStats().setSessions(vp.getStats().getSessions() + normalSessions);

			if(!ConfigEntry.CATCH_UP_SESSIONS.getValueAsBoolean() && vp.getStats().getSessions() > (normalSessions + preProduceSessions)) {
				vp.getStats().setSessions(normalSessions + preProduceSessions);
			}
		}

		if(ConfigEntry.CATCH_UP_SESSIONS.getValueAsBoolean())
			Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.ALERT, ConfigMessages.ALERT_NEW_SESSIONS_FOR_ALL.getValue().replace("%newSessions%", String.valueOf(ConfigEntry.SESSIONS_PER_DAY.getValueAsInt())));
		else
			Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.ALERT, ConfigMessages.ALERT_NEW_SESSIONS.getValue().replace("%newSessions%", String.valueOf(ConfigEntry.SESSIONS_PER_DAY.getValueAsInt())));
	}
}