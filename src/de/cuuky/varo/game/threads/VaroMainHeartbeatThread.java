package de.cuuky.varo.game.threads;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.time.DateUtils;
import org.bukkit.scheduler.BukkitRunnable;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.event.BukkitEventType;
import de.cuuky.varo.entity.player.stats.stat.PlayerState;
import de.cuuky.varo.event.VaroEvent;
import de.cuuky.varo.event.VaroEventType;
import de.cuuky.varo.game.VaroGame;
import de.cuuky.varo.game.state.GameState;

public class VaroMainHeartbeatThread extends BukkitRunnable {

	private int protectionTime, noKickDistance, playTime;
	private VaroGame game;

	public VaroMainHeartbeatThread() {
		this.game = Main.getVaroGame();

		loadVariables();
	}

	public void loadVariables() {
		protectionTime = ConfigSetting.JOIN_PROTECTIONTIME.getValueAsInt();
		noKickDistance = ConfigSetting.NO_KICK_DISTANCE.getValueAsInt();
		playTime = ConfigSetting.PLAY_TIME.getValueAsInt() * 60;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		if (game.getGameState() == GameState.STARTED) {
			if (ConfigSetting.KICK_AT_SERVER_CLOSE.getValueAsBoolean()) {
				int secondsToClose = (int) TimeUnit.SECONDS.convert(Main.getDataManager().getOutsideTimeChecker().getDate2().getTime().getTime() - new Date().getTime(), TimeUnit.MILLISECONDS);
				if (secondsToClose % 60 == 0) {
					int minutesToClose = secondsToClose / 60;
					if (minutesToClose == 10 || minutesToClose == 5 || minutesToClose == 3 || minutesToClose == 2 || minutesToClose == 1)
						Main.getLanguageManager().broadcastMessage(ConfigMessages.QUIT_KICK_SERVER_CLOSE_SOON).replace("%minutes%", String.valueOf(minutesToClose));

					if (!Main.getDataManager().getOutsideTimeChecker().canJoin()) {
						for (VaroPlayer vp : (ArrayList<VaroPlayer>) VaroPlayer.getOnlinePlayer().clone()) {
							if (vp.isAdminIgnore())
								continue;

							vp.getStats().setCountdown(0);
							vp.getPlayer().kickPlayer("§cDie Spielzeit ist nun vorueber!\n§7Versuche es morgen erneut");
						}
					}
				}

			}

			if (ConfigSetting.PLAY_TIME.isIntActivated()) {
				for (VaroPlayer vp : (ArrayList<VaroPlayer>) VaroPlayer.getOnlinePlayer().clone()) {
					if (vp.getStats().isSpectator() || vp.isAdminIgnore())
						continue;

					int countdown = Math.max(vp.getStats().getCountdown() - 1, 0);

					if (countdown == playTime - protectionTime - 1 && !game.isFirstTime() && !VaroEvent.getEvent(VaroEventType.MASS_RECORDING).isEnabled())
						Main.getLanguageManager().broadcastMessage(ConfigMessages.JOIN_PROTECTION_OVER, vp);

					if (countdown == 30 || countdown == 10 || countdown == 5 || countdown == 4 || countdown == 3 || countdown == 2 || countdown == 1 || countdown == 0) {
						if (countdown == 0 && !VaroEvent.getEvent(VaroEventType.MASS_RECORDING).isEnabled()) {
							Main.getLanguageManager().broadcastMessage(ConfigMessages.QUIT_KICK_BROADCAST, vp);
							vp.onEvent(BukkitEventType.KICKED);
							vp.getPlayer().kickPlayer(ConfigMessages.KICK_SESSION_OVER.getValue(null, vp));
							continue;
						} else {
							if (countdown == 1) {
								if (!vp.canBeKicked(noKickDistance)) {
									vp.sendMessage(ConfigMessages.QUIT_KICK_PLAYER_NEARBY).replace("%distance%", String.valueOf(ConfigSetting.NO_KICK_DISTANCE.getValueAsInt()));
									countdown += 1;
								} else
									Main.getLanguageManager().broadcastMessage(ConfigMessages.QUIT_KICK_IN_SECONDS, vp).replace("%countdown%", (countdown == 1) ? "einer" : String.valueOf(countdown));
							} else
								Main.getLanguageManager().broadcastMessage(ConfigMessages.QUIT_KICK_IN_SECONDS, vp).replace("%countdown%", (countdown == 1) ? "einer" : String.valueOf(countdown));
						}
					}

					vp.getStats().setCountdown(countdown);
				}
			}

			for (VaroPlayer vp : (ArrayList<VaroPlayer>) VaroPlayer.getOnlinePlayer().clone())
				if(!vp.isAdminIgnore() && vp.getStats().isAlive())
					vp.getStats().increaseOnlineTime();
			
			this.game.setProjectTime(this.game.getProjectTime() + 1L);
		}

		for (VaroPlayer vp : VaroPlayer.getOnlinePlayer()) {
			if (game.getGameState() == GameState.LOBBY) {
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