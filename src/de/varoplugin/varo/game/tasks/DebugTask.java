package de.varoplugin.varo.game.tasks;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class DebugTask extends VaroTask {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        System.out.println("INTERACT: " + event.getPlayer().getName());
    }
}