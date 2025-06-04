package de.varoplugin.varo.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import de.varoplugin.varo.Main;
import de.varoplugin.varo.combatlog.CombatlogCheck;
import de.varoplugin.varo.config.language.Messages;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;
import de.varoplugin.varo.logger.logger.EventLogger.LogType;
import de.varoplugin.varo.player.VaroPlayer;
import de.varoplugin.varo.player.VaroPlayerDisconnect;
import de.varoplugin.varo.player.event.BukkitEventType;
import de.varoplugin.varo.player.stats.stat.PlayerState;

public class PlayerQuitListener implements Listener {

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		VaroPlayer vplayer = VaroPlayer.getPlayer(player);
		event.setQuitMessage(null);

		Messages.LOG_PLAYER_QUIT.log(LogType.JOIN_LEAVE, vplayer);

		// IF THEY WERE A SPECTATOR
		if (vplayer.getStats().isSpectator() || vplayer.isAdminIgnore()) {
			Messages.PLAYER_DISCONNECT_SPECTATOR.broadcast(vplayer);
			vplayer.onEvent(BukkitEventType.QUIT);
			return;
		}

		if (Main.getVaroGame().isRunning()) {
			// IF THEY WERE KICKED OR DEAD
			if (vplayer.getStats().getState() == PlayerState.DEAD || !vplayer.getStats().hasTimeLeft() && Main.getVaroGame().isPlayTimeLimited()) {
				vplayer.onEvent(BukkitEventType.QUIT);
				if (vplayer.getStats().getState() != PlayerState.DEAD)
				    Messages.LOG_KICKED_PLAYER.log(LogType.JOIN_LEAVE, vplayer);
				return;
			}

			// CHECK IF THEY COMBATLOGGED
			CombatlogCheck check = new CombatlogCheck(player);
			if (check.isCombatLog()) {
				check.punish();
				vplayer.onEvent(BukkitEventType.QUIT);
				return;
			}

			// CHECK DISCONNECTS
			if (vplayer.getStats().hasTimeLeft() || !Main.getVaroGame().isPlayTimeLimited()) {
				if (ConfigSetting.DISCONNECT_PER_SESSION.isIntActivated()) {
					VaroPlayerDisconnect dc = VaroPlayerDisconnect.getDisconnect(player);
					if (dc == null)
						dc = new VaroPlayerDisconnect(player);
					dc.addDisconnect();

					if (dc.check()) {
						vplayer.onEvent(BukkitEventType.QUIT);
						return;
					}
				}

				VaroPlayerDisconnect.disconnected(vplayer);
				Messages.PLAYER_DISCONNECT_TOO_ERALY.broadcast(vplayer);
				Messages.LOG_PLAYER_DC_TOO_EARLY.log(LogType.JOIN_LEAVE, vplayer);
				vplayer.onEvent(BukkitEventType.QUIT);
				return;
			}
		}

		vplayer.onEvent(BukkitEventType.QUIT);
		Messages.PLAYER_DISCONNECT_BROADCAST.broadcast(vplayer);
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerKick(PlayerKickEvent event) {
	    if (event.isCancelled() || !ConfigSetting.DISCONNECT_IGNORE_KICK.getValueAsBoolean())
	        return;
	    Player player = event.getPlayer();
	    VaroPlayerDisconnect dc = VaroPlayerDisconnect.getDisconnect(player);
	    if (dc == null)
	        dc = new VaroPlayerDisconnect(player);
	    dc.setKick(true);
	}
}