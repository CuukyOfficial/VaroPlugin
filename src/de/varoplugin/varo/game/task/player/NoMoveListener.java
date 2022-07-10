package de.varoplugin.varo.game.task.player;

import de.varoplugin.varo.game.entity.player.Player;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;

public class NoMoveListener extends AbstractPlayerListener {

    public NoMoveListener(Player player) {
        super(player);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!this.getPlayer().isPlayer(event.getPlayer())) return;
        Location from = event.getFrom();
        Location to = event.getTo();
        if (from.getBlockX() == to.getBlockX() && from.getBlockZ() == to.getBlockZ()) return;
        event.setTo(from);
    }
}
