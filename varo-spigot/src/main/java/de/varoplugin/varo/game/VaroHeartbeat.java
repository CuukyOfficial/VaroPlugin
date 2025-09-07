package de.varoplugin.varo.game;

import de.varoplugin.varo.Main;
import de.varoplugin.varo.config.language.Messages;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;
import de.varoplugin.varo.event.VaroEvent;
import de.varoplugin.varo.event.VaroEventType;
import de.varoplugin.varo.player.VaroPlayer;
import de.varoplugin.varo.player.event.BukkitEventType;
import de.varoplugin.varo.player.stats.stat.PlayerState;
import io.github.almightysatan.slams.Placeholder;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class VaroHeartbeat implements Runnable {

	private final int protectionTime, noKickDistance, playTime;
	private final VaroGame game;

	public VaroHeartbeat(VaroGame game) {
		this.game = game;
		this.protectionTime = ConfigSetting.JOIN_PROTECTIONTIME.getValueAsInt();
		this.noKickDistance = ConfigSetting.NO_KICK_DISTANCE.getValueAsInt();
		this.playTime = game.getPlayTime() * 60;
	}

	@Override
	public void run() {
		if (this.game.isRunning()) {
			if (ConfigSetting.KICK_AT_SERVER_CLOSE.getValueAsBoolean()) {
				int secondsToClose = (int) TimeUnit.SECONDS.convert(Main.getDataManager().getOutsideTimeChecker().getDate2().getTime().getTime() - new Date().getTime(), TimeUnit.MILLISECONDS);
				if (secondsToClose % 60 == 0) {
					int minutesToClose = secondsToClose / 60;
					if (minutesToClose == 10 || minutesToClose == 5 || minutesToClose == 3 || minutesToClose == 2 || minutesToClose == 1)
					    Messages.PLAYER_DISCONNECT_KICK_SERVER_CLOSE_SOON.broadcast(Placeholder.constant("server-close", String.valueOf(minutesToClose)));

					if (!Main.getDataManager().getOutsideTimeChecker().canJoin()) {
						for (VaroPlayer vp : VaroPlayer.getOnlinePlayer()) {
							if (vp.isAdminIgnore())
								continue;

							vp.getStats().setCountdown(0);
							Messages.PLAYER_KICK_SERVER_CLOSE.kick(vp);
						}
					}
				}

			}

			if (Main.getVaroGame().isPlayTimeLimited()) {
				for (VaroPlayer vp : VaroPlayer.getOnlinePlayer()) {
					if (vp.getStats().isSpectator() || vp.isAdminIgnore())
						continue;

					int countdown = Math.max(vp.getStats().getCountdown() - 1, 0);

					boolean massRecordingEnabled = VaroEvent.getEvent(VaroEventType.MASS_RECORDING).isEnabled();
					if (countdown == playTime - protectionTime - 1 && !game.isFirstTime() && !massRecordingEnabled)
						Messages.PLAYER_JOIN_PROTECTION_END.broadcast(vp);

					if (countdown == 30 || countdown == 10 || countdown <= 5) {
						if (countdown == 0 && !massRecordingEnabled) {
							Messages.PLAYER_DISCONNECT_KICK.broadcast(vp);
							vp.onEvent(BukkitEventType.KICKED);
							Messages.PLAYER_KICK_SESSION_OVER.kick(vp);
							continue;
						}
                        if (countdown == 1) {
                        	if (!vp.canBeKicked(noKickDistance)) {
                        	    Messages.PLAYER_DISCONNECT_KICK_PLAYER_NEARBY.send(vp);
                        		countdown += 1;
                        	} else
                        	    Messages.PLAYER_DISCONNECT_KICK_IN_SECONDS.broadcast(vp, Placeholder.constant("kick-delay", String.valueOf(countdown)));
                        } else
                            Messages.PLAYER_DISCONNECT_KICK_IN_SECONDS.broadcast(vp, Placeholder.constant("kick-delay", String.valueOf(countdown)));
					}

					vp.getStats().setCountdown(countdown);
				}
			}

			for (VaroPlayer vp : VaroPlayer.getOnlinePlayer())
				if(!vp.isAdminIgnore() && vp.getStats().isAlive())
					vp.getStats().increaseOnlineTime();
			
			this.game.setProjectTime(this.game.getProjectTime() + 1L);
		}

		for (VaroPlayer vp : VaroPlayer.getOnlinePlayer()) {
			if (!this.game.hasStarted()) { // TODO why tf is this executed every tick???
				vp.getStats().setCountdown(playTime);
				vp.setAdminIgnore(false);
				if (vp.getStats().getState() == PlayerState.DEAD)
					vp.getStats().setState(PlayerState.ALIVE);
			}

			vp.update();
		}

		if (ConfigSetting.SESSIONS_PER_DAY.getValueAsInt() <= 0) {
			for (VaroPlayer vp : VaroPlayer.getVaroPlayers()) {
				if (vp.getStats().getTimeUntilAddSession() == null) {
					continue;
				}
				if (new Date().after(vp.getStats().getTimeUntilAddSession())) {
					vp.getStats().setSessions(vp.getStats().getSessions() + 1);
					if (vp.getStats().getSessions() < ConfigSetting.PRE_PRODUCE_SESSIONS.getValueAsInt() + 1) {
						vp.getStats().setTimeUntilAddSession(DateUtils.addHours(new Date(), ConfigSetting.JOIN_AFTER_HOURS.getValueAsInt()));
					} else {
						vp.getStats().setTimeUntilAddSession(null);
					}
				}
			}
		}
	}
}