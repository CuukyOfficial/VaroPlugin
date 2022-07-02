package de.varoplugin.varo.game.task.player;

import de.varoplugin.varo.game.entity.player.VaroPlayer;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class NoMoveListener extends AbstractPlayerListener {

    public NoMoveListener(VaroPlayer player) {
        super(player);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!this.getPlayer().isPlayer(event.getPlayer())) return;
        Location from = event.getFrom();
        Location to = event.getTo();
        if (from.getBlockX() == to.getBlockX() && from.getBlockZ() == to.getBlockZ()) return;
        event.setTo(from);
    }
}
