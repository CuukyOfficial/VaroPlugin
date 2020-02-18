package de.cuuky.varo.game.threads;

import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.config.ConfigEntry;
import de.cuuky.varo.configuration.messages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.event.BukkitEventType;
import de.cuuky.varo.entity.player.stats.stat.PlayerState;
import de.cuuky.varo.event.VaroEvent;
import de.cuuky.varo.game.VaroGame;
import de.cuuky.varo.game.state.GameState;
import de.cuuky.varo.utils.JavaUtils;

public class VaroMainHeartbeatThread implements Runnable {
	
	private int seconds, protectionTime, noKickDistance, playTime;
	private boolean showDistanceToBorder, showTimeInActionBar;
	private VaroGame game;
	
	public VaroMainHeartbeatThread() {
		this.seconds = 0;
		this.game = Main.getVaroGame();
				
		loadVariables();
	}
	
	public void loadVariables() {
		showDistanceToBorder = ConfigEntry.SHOW_DISTANCE_TO_BORDER.getValueAsBoolean();
		showTimeInActionBar = ConfigEntry.SHOW_TIME_IN_ACTIONBAR.getValueAsBoolean();
		protectionTime = ConfigEntry.JOIN_PROTECTIONTIME.getValueAsInt();
		noKickDistance = ConfigEntry.NO_KICK_DISTANCE.getValueAsInt();
		playTime = ConfigEntry.PLAY_TIME.getValueAsInt() * 60;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		seconds++;
		if(game.getGameState() == GameState.STARTED) {
			if(seconds == 60) {
				seconds = 0;
				if(ConfigEntry.KICK_AT_SERVER_CLOSE.getValueAsBoolean()) {
					double minutesToClose = (int) (((Main.getDataManager().getOutsideTimeChecker().getDate2().getTime().getTime() - new Date().getTime()) / 1000) / 60);

					if(minutesToClose == 10 || minutesToClose == 5 || minutesToClose == 3 || minutesToClose == 2 || minutesToClose == 1)
						Bukkit.broadcastMessage(ConfigMessages.KICK_SERVER_CLOSE_SOON.getValue().replace("%minutes%", String.valueOf(minutesToClose)));

					if(!Main.getDataManager().getOutsideTimeChecker().canJoin())
						for(VaroPlayer vp : (ArrayList<VaroPlayer>) VaroPlayer.getOnlinePlayer().clone()) {
							vp.getStats().setCountdown(0);
							vp.getPlayer().kickPlayer("§cDie Spielzeit ist nun vorüber!\n§7Versuche es morgen erneut");
						}
				}
			}

			if(ConfigEntry.PLAY_TIME.isIntActivated()) {
				for(VaroPlayer vp : (ArrayList<VaroPlayer>) VaroPlayer.getOnlinePlayer().clone()) {
					if(vp.getStats().isSpectator() || vp.isAdminIgnore())
						continue;

					int countdown = vp.getStats().getCountdown() - 1;
					Player p = vp.getPlayer();
					ArrayList<String> actionbar = new ArrayList<>();

					if(showTimeInActionBar || vp.getStats().isShowActionbarTime())
						actionbar.add(Main.getColorCode() + vp.getStats().getCountdownMin(countdown) + "§8:" + Main.getColorCode() + vp.getStats().getCountdownSec(countdown));
					if(showDistanceToBorder) {
						int distance = (int) Main.getVaroGame().getVaroWorld().getVaroBorder().getBorderDistanceTo(p);
						if(!ConfigEntry.DISTANCE_TO_BORDER_REQUIRED.isIntActivated() || distance <= ConfigEntry.DISTANCE_TO_BORDER_REQUIRED.getValueAsInt())
							actionbar.add("§7Distanz zur Border: " + Main.getColorCode() + distance);
					}

					if(!actionbar.isEmpty())
						vp.getNetworkManager().sendActionbar(JavaUtils.getArgsToString(actionbar, "§7 | "));

					if(countdown == playTime - protectionTime - 1 && !game.isFirstTime() && !VaroEvent.getMassRecEvent().isEnabled())
						Bukkit.broadcastMessage(ConfigMessages.JOIN_PROTECTION_OVER.getValue(vp));

					if(countdown == 30 || countdown == 10 || countdown == 5 || countdown == 4 || countdown == 3 || countdown == 2 || countdown == 1 || countdown == 0) {
						if(countdown == 0 && !VaroEvent.getMassRecEvent().isEnabled()) {
							Bukkit.broadcastMessage(ConfigMessages.KICK_BROADCAST.getValue(vp));
							vp.onEvent(BukkitEventType.KICKED);
							p.kickPlayer(ConfigMessages.KICK_MESSAGE.getValue(vp));
							continue;
						} else {
							if(countdown == 1)
								if(!vp.canBeKicked(noKickDistance)) {
									vp.sendMessage(ConfigMessages.KICK_PLAYER_NEARBY.getValue().replace("%distance%", String.valueOf(ConfigEntry.NO_KICK_DISTANCE.getValueAsInt())));
									countdown += 1;
								}

							Bukkit.broadcastMessage(ConfigMessages.KICK_IN_SECONDS.getValue().replace("%player%", vp.getName()).replace("%countdown%", countdown == 1 ? "einer" : String.valueOf(countdown)));
						}
					}

					vp.getStats().setCountdown(countdown);
				}
			}
		}

		for(VaroPlayer vp : VaroPlayer.getOnlinePlayer()) {
			if(game.getGameState() == GameState.LOBBY) {
				vp.getStats().setCountdown(playTime);
				vp.setAdminIgnore(false);
				if(vp.getStats().getState() == PlayerState.DEAD)
					vp.getStats().setState(PlayerState.ALIVE);
			}

			Main.getDataManager().getScoreboardHandler().update(vp);
			vp.update();
		}

		if(ConfigEntry.SESSIONS_PER_DAY.getValueAsInt() <= 0) {
			for(VaroPlayer vp : VaroPlayer.getVaroPlayer()) {
				if(vp.getStats().getTimeUntilAddSession() == null) {
					continue;
				}
				if(new Date().after(vp.getStats().getTimeUntilAddSession())) {
					vp.getStats().setSessions(vp.getStats().getSessions() + 1);
					if(vp.getStats().getSessions() < ConfigEntry.PRE_PRODUCE_SESSIONS.getValueAsInt() + 1) {
						vp.getStats().setTimeUntilAddSession(DateUtils.addHours(new Date(), ConfigEntry.JOIN_AFTER_HOURS.getValueAsInt()));
					} else {
						vp.getStats().setTimeUntilAddSession(null);
					}
				}
			}
		}
	}
}