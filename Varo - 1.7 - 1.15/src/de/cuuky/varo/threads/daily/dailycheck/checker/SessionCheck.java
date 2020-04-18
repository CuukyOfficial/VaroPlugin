package de.cuuky.varo.threads.daily.dailycheck.checker;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.messages.language.languages.defaults.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.logger.logger.EventLogger.LogType;
import de.cuuky.varo.threads.daily.dailycheck.Checker;

public class SessionCheck extends Checker {

	@Override
	public void check() {
		if(ConfigSetting.SESSIONS_PER_DAY.getValueAsInt() <= 0) {
			return;
		}

		int normalSessions = ConfigSetting.SESSIONS_PER_DAY.getValueAsInt();
		int preProduceSessions;
		if(ConfigSetting.PRE_PRODUCE_SESSIONS.getValueAsInt() > 0) {
			preProduceSessions = ConfigSetting.PRE_PRODUCE_SESSIONS.getValueAsInt();
		} else {
			preProduceSessions = 0;
		}

		for(VaroPlayer vp : VaroPlayer.getVaroPlayer()) {

			vp.getStats().setSessions(vp.getStats().getSessions() + normalSessions);

			if(!ConfigSetting.CATCH_UP_SESSIONS.getValueAsBoolean() && vp.getStats().getSessions() > (normalSessions + preProduceSessions)) {
				vp.getStats().setSessions(normalSessions + preProduceSessions);
			}
		}

		if(ConfigSetting.CATCH_UP_SESSIONS.getValueAsBoolean())
			Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.ALERT, ConfigMessages.ALERT_NEW_SESSIONS_FOR_ALL.getValue().replace("%newSessions%", String.valueOf(ConfigSetting.SESSIONS_PER_DAY.getValueAsInt())));
		else
			Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.ALERT, ConfigMessages.ALERT_NEW_SESSIONS.getValue().replace("%newSessions%", String.valueOf(ConfigSetting.SESSIONS_PER_DAY.getValueAsInt())));
	}
}