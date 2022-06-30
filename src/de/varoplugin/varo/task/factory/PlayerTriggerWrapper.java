package de.varoplugin.varo.task.factory;

import de.varoplugin.varo.api.event.game.player.VaroPlayerRemoveEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerTriggerWrapper extends AbstractTriggerWrapper {

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoin(PlayerJoinEvent event) {
        this.test();
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerQuit(PlayerQuitEvent event) {
        this.test();
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoin(VaroPlayerRemoveEvent event) {
        this.test();
    }
}
