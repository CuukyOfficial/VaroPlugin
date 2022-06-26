package de.varoplugin.varo.jobs.game;

import de.varoplugin.varo.jobs.AbstractVaroListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerLoginEvent;

public class LobbyLoginListener extends AbstractVaroListener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onLobbyLogin(PlayerLoginEvent event) {
        if (event.getResult() != PlayerLoginEvent.Result.ALLOWED) return;

        // TODO: Add register before start config option and make default player state configurable
        this.getVaro().register(event.getPlayer());
    }
}