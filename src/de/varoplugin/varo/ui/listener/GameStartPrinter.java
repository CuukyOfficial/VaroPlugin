package de.varoplugin.varo.ui.listener;

import de.varoplugin.varo.api.event.game.VaroStateChangeEvent;
import de.varoplugin.varo.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;

public class GameStartPrinter extends UiListener {

    @EventHandler
    public void onGameStart(VaroStateChangeEvent event) {
        if (event.getState() != GameState.RUNNING) return;
        Bukkit.broadcastMessage("Game has been started!");
    }
}
