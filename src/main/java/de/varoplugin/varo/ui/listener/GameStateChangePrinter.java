package de.varoplugin.varo.ui.listener;

import de.varoplugin.varo.api.event.game.VaroStateChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;

public class GameStateChangePrinter extends UiListener {

    @EventHandler
    public void onGameStart(VaroStateChangeEvent event) {
        Bukkit.broadcastMessage("New game state: " + event.getState().toString());
    }
}
