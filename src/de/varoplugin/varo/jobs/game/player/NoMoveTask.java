package de.varoplugin.varo.jobs.game.player;

import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.jobs.AbstractVaroListener;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Resets the position of the player if he has moved.
 */
public class NoMoveTask extends AbstractVaroListener {

    private final VaroPlayer player;

    public NoMoveTask(VaroPlayer player) {
        this.player = player;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!event.getPlayer().getUniqueId().equals(this.player.getUuid())) return;
        Location current = event.getTo();
        Location last = event.getFrom();

        if (current.getBlockX() == last.getBlockX() && current.getBlockZ() == last.getBlockZ())
            return;

        event.getPlayer().teleport(last);
    }
}