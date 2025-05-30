package de.varoplugin.varo.threads.daily.checks;

import de.varoplugin.varo.config.language.Messages;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;
import de.varoplugin.varo.logger.logger.EventLogger.LogType;
import de.varoplugin.varo.player.VaroPlayer;
import de.varoplugin.varo.player.VaroPlayerDisconnect;
import de.varoplugin.varo.player.event.BukkitEventType;
import de.varoplugin.varo.threads.daily.Checker;
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

		if (ConfigSetting.CATCH_UP_SESSIONS.getValueAsBoolean()) // TODO these messages should be improved
		    Messages.LOG_NEW_SESSIONS_FOR_ALL.log(LogType.LOG, Placeholder.constant("new-sessions", String.valueOf(ConfigSetting.SESSIONS_PER_DAY.getValueAsInt())));
		else
		    Messages.LOG_NEW_SESSIONS.log(LogType.LOG, Placeholder.constant("new-sessions", String.valueOf(ConfigSetting.SESSIONS_PER_DAY.getValueAsInt())));
	}
}