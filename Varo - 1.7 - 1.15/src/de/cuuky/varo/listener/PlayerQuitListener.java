package de.cuuky.varo.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import de.cuuky.varo.Main;
import de.cuuky.varo.combatlog.CombatlogCheck;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.messages.language.languages.LanguageDE;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.disconnect.VaroPlayerDisconnect;
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
			event.setQuitMessage(Main.getLanguageManager().getValue(LanguageDE.QUIT_SPECTATOR, null, vplayer));
			vplayer.onEvent(BukkitEventType.QUIT);
			return;
		}

		if(Main.getVaroGame().getGameState() == GameState.STARTED) {
			// IF THEY WERE KICKED OR DEAD
			if(ConfigSetting.PLAY_TIME.isIntActivated())
				if(vplayer.getStats().getState() == PlayerState.DEAD || !vplayer.getStats().hasTimeLeft()) {
					vplayer.onEvent(BukkitEventType.QUIT);
					if(vplayer.getStats().getState() != PlayerState.DEAD)
						Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.JOIN_LEAVE, Main.getLanguageManager().getValue(LanguageDE.ALERT_KICKED_PLAYER, null, vplayer));
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
				if(ConfigSetting.DISCONNECT_PER_SESSION.isIntActivated()) {
					VaroPlayerDisconnect dc = VaroPlayerDisconnect.getDisconnect(player);
					if(dc == null)
						dc = new VaroPlayerDisconnect(player);
					dc.addDisconnect();

					if(dc.check()) {
						vplayer.onEvent(BukkitEventType.QUIT);
						return;
					}
				}

				VaroPlayerDisconnect.disconnected(vplayer.getName());
				Bukkit.broadcastMessage(Main.getLanguageManager().getValue(LanguageDE.QUIT_WITH_REMAINING_TIME, null, vplayer));
				Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.JOIN_LEAVE, Main.getLanguageManager().getValue(LanguageDE.ALERT_PLAYER_DC_TO_EARLY, null, vplayer));
				vplayer.onEvent(BukkitEventType.QUIT);
				return;
			}
		}

		vplayer.onEvent(BukkitEventType.QUIT);
		event.setQuitMessage(Main.getLanguageManager().getValue(LanguageDE.QUIT_MESSAGE, null, vplayer));
	}
}