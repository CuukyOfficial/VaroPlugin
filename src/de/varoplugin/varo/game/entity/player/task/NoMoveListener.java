package de.varoplugin.varo.game.entity.player.task;

import de.varoplugin.varo.game.entity.player.VaroPlayer;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

// Not sure if this should be a heartbeat task.
/**
 * Resets the position of the player if he has moved.
 *
 * @author CuukyOfficial
 * @version v0.1
 */
public class NoMoveListener extends VaroPlayerOnlineTask {

    public NoMoveListener(VaroPlayer player) {
        super(player);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Location current = event.getTo();
        Location last = event.getFrom();

        if (current.getBlockX() == last.getBlockX() && current.getBlockZ() == last.getBlockZ())
            return;

        event.getPlayer().teleport(last);
    }
}