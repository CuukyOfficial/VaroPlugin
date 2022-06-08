package de.varoplugin.varo.game.tasks.game.player;

import de.varoplugin.varo.game.entity.player.VaroPlayer;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Resets the position of the player if he has moved.
 *
 * @author CuukyOfficial
 * @version v0.1
 */
public class NoMoveTask extends VaroGameTask {

    public NoMoveTask(VaroPlayer player) {
        super(player);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!event.getPlayer().equals(this.player.getPlayer())) return;
        Location current = event.getTo();
        Location last = event.getFrom();

        if (current.getBlockX() == last.getBlockX() && current.getBlockZ() == last.getBlockZ())
            return;

        event.getPlayer().teleport(last);
    }
}