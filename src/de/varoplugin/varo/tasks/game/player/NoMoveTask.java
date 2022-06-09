package de.varoplugin.varo.tasks.game.player;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Resets the position of the player if he has moved.
 *
 * @author CuukyOfficial
 * @version v0.1
 */
public class NoMoveTask extends AbstractPlayerTask {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (this.shallIgnore(event)) return;
        Location current = event.getTo();
        Location last = event.getFrom();

        if (current.getBlockX() == last.getBlockX() && current.getBlockZ() == last.getBlockZ())
            return;

        event.getPlayer().teleport(last);
    }
}