package de.varoplugin.varo.task.factory;

import de.varoplugin.varo.api.event.game.VaroStateChangeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class GameTriggerWrapper extends AbstractTriggerWrapper {

    @EventHandler(priority = EventPriority.HIGH)
    public void onGameStateChange(VaroStateChangeEvent event) {
        this.test();
    }
}
