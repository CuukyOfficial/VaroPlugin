package de.varoplugin.varo.tasks.game;

import de.varoplugin.varo.tasks.AbstractVaroTask;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerLoginEvent;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class LobbyLoginTask extends AbstractVaroTask {

    @EventHandler(priority = EventPriority.HIGH)
    public void onLobbyLogin(PlayerLoginEvent event) {
        if (event.getResult() != PlayerLoginEvent.Result.ALLOWED) return;

        // TODO: Add register before start config option and make default player state configurable
        this.varo.register(event.getPlayer());
    }
}