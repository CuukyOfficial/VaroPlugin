package de.cuuky.varo.listener;

import de.cuuky.varo.logger.LoggerMaster;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import de.cuuky.varo.Main;
import de.cuuky.varo.combatlog.CombatlogCheck;
import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.config.messages.ConfigMessages;
import de.cuuky.varo.disconnect.Disconnect;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.event.BukkitEventType;
import de.cuuky.varo.entity.player.stats.stat.PlayerState;
import de.cuuky.varo.game.state.GameState;
import de.cuuky.varo.logger.logger.EventLogger.LogType;

public class PlayerQuitListener implements Listener {

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		VaroPlayer vplayer = VaroPlayer.getPlayer(player);
		event.setQuitMessage(null);

		// IF THEY WERE A SPECTATOR
		if(vplayer.getStats().isSpectator() || vplayer.isAdminIgnore()) {
			event.setQuitMessage(ConfigMessages.QUIT_SPECTATOR.getValue(vplayer));
			vplayer.onEvent(BukkitEventType.QUIT);
			return;
		}

		if(Main.getGame().getGameState() == GameState.STARTED) {
			// IF THEY WERE KICKED OR DEAD
			if(ConfigEntry.PLAY_TIME.isIntActivated())
				if(vplayer.getStats().getState() == PlayerState.DEAD || !vplayer.getStats().hasTimeLeft()) {
					vplayer.onEvent(BukkitEventType.QUIT);
					if(vplayer.getStats().getState() != PlayerState.DEAD)
						LoggerMaster.getInstance().getEventLogger().println(LogType.JOIN_LEAVE, ConfigMessages.ALERT_KICKED_PLAYER.getValue(vplayer));
					return;
				}

			// CHECK IF THEY COMBATLOGGED
			CombatlogCheck check = new CombatlogCheck(event);
			if(check.isCombatLog()) {
				vplayer.onEvent(BukkitEventType.QUIT);
				return;
			}

			// CHECK DISCONNECTS
			if(vplayer.getStats().hasTimeLeft()) {
				if(ConfigEntry.DISCONNECT_PER_SESSION.isIntActivated()) {
					Disconnect dc = Disconnect.getDisconnect(player);
					if(dc == null)
						dc = new Disconnect(player);
					dc.addDisconnect();

					if(dc.check()) {
						vplayer.onEvent(BukkitEventType.QUIT);
						return;
					}
				}

				Disconnect.disconnected(vplayer.getName());
				Bukkit.broadcastMessage(ConfigMessages.QUIT_WITH_REMAINING_TIME.getValue(vplayer));
				LoggerMaster.getInstance().getEventLogger().println(LogType.JOIN_LEAVE, ConfigMessages.ALERT_PLAYER_DC_TO_EARLY.getValue(vplayer));
				vplayer.onEvent(BukkitEventType.QUIT);
				return;
			}
		}

		vplayer.onEvent(BukkitEventType.QUIT);
		event.setQuitMessage(ConfigMessages.QUIT.getValue(vplayer));
	}
}