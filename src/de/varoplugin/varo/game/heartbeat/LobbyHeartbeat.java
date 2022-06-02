package de.varoplugin.varo.game.heartbeat;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class LobbyHeartbeat extends AbstractHeartbeat {

    @Override
    public void run() {

    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        event.setCancelled(true);
    }
}