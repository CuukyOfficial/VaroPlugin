package de.cuuky.varo.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import de.cuuky.varo.Main;
import de.cuuky.varo.combatlog.CombatlogCheck;
import de.cuuky.varo.config.language.Messages;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.logger.logger.EventLogger.LogType;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.player.VaroPlayerDisconnect;
import de.cuuky.varo.player.event.BukkitEventType;
import de.cuuky.varo.player.stats.stat.PlayerState;

public class PlayerQuitListener implements Listener {

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		VaroPlayer vplayer = VaroPlayer.getPlayer(player);
		event.setQuitMessage(null);

		// IF THEY WERE A SPECTATOR
		if (vplayer.getStats().isSpectator() || vplayer.isAdminIgnore()) {
			Main.getLanguageManager().broadcastMessage(ConfigMessages.QUIT_SPECTATOR, vplayer);
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
				Main.getLanguageManager().broadcastMessage(ConfigMessages.QUIT_WITH_REMAINING_TIME, vplayer);
				Messages.LOG_PLAYER_DC_TO_EARLY.log(LogType.JOIN_LEAVE, vplayer);
				vplayer.onEvent(BukkitEventType.QUIT);
				return;
			}
		}

		vplayer.onEvent(BukkitEventType.QUIT);
		Main.getLanguageManager().broadcastMessage(ConfigMessages.QUIT_MESSAGE, vplayer);
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