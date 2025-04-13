package de.cuuky.varo.threads.daily.checks;

import de.cuuky.varo.config.language.Messages;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.logger.logger.EventLogger.LogType;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.player.VaroPlayerDisconnect;
import de.cuuky.varo.player.event.BukkitEventType;
import de.cuuky.varo.threads.daily.Checker;
import io.github.almightysatan.slams.Placeholder;

public class SessionCheck extends Checker {

	@Override
	public void check() {
		VaroPlayerDisconnect.clearDisconnects();

		if (ConfigSetting.SESSIONS_PER_DAY.getValueAsInt() <= 0) {
			return;
		}

		int normalSessions = ConfigSetting.SESSIONS_PER_DAY.getValueAsInt();
		int preProduceSessions = Math.max(ConfigSetting.PRE_PRODUCE_SESSIONS.getValueAsInt(), 0);

		for (VaroPlayer vp : VaroPlayer.getVaroPlayers()) {
			if (!vp.getStats().hasFullTime()) {
				if (vp.isOnline())
				    Messages.PLAYER_KICK_SESSION_OVER.kick(vp);

				vp.onEvent(BukkitEventType.KICKED);
				Messages.LOG_SESSIONS_ENDED.log(LogType.LOG, vp);
			}

			vp.getStats().setSessions(vp.getStats().getSessions() + normalSessions);

			if (!ConfigSetting.CATCH_UP_SESSIONS.getValueAsBoolean() && vp.getStats().getSessions() > (normalSessions + preProduceSessions))
				vp.getStats().setSessions(normalSessions + preProduceSessions);
		}

		if (ConfigSetting.CATCH_UP_SESSIONS.getValueAsBoolean())
		    Messages.LOG_NEW_SESSIONS_FOR_ALL.log(LogType.LOG, Placeholder.constant("new-sessions", String.valueOf(ConfigSetting.SESSIONS_PER_DAY.getValueAsInt())));
		else
		    Messages.LOG_NEW_SESSIONS.log(LogType.LOG, Placeholder.constant("new-sessions", String.valueOf(ConfigSetting.SESSIONS_PER_DAY.getValueAsInt())));
	}
}