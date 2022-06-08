package de.varoplugin.varo.game.tasks.game;

import de.varoplugin.varo.game.tasks.VaroGameTask;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerLoginEvent;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class LobbyLoginTask extends VaroGameTask {

    @EventHandler(priority = EventPriority.HIGH)
    public void onLobbyLogin(PlayerLoginEvent event) {
        if (event.getResult() != PlayerLoginEvent.Result.ALLOWED) return;

        // TODO: Add register before start config option
        this.varo.register(event.getPlayer());
    }
}