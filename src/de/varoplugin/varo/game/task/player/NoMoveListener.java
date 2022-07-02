package de.varoplugin.varo.game.task.player;

import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.task.AbstractListener;
import de.varoplugin.varo.task.VaroTask;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class NoMoveListener extends AbstractListener {

    private VaroPlayer player;

    public NoMoveListener(VaroPlayer player) {
        super(player.getVaro());
        this.player = player;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!this.player.isPlayer(event.getPlayer())) return;
        Location from = event.getFrom();
        Location to = event.getTo();
        if (from.getBlockX() == to.getBlockX() && from.getBlockZ() == to.getBlockZ()) return;
        event.setTo(from);
    }

    @Override
    public VaroTask clone() {
        NoMoveListener listener = (NoMoveListener) super.clone();
        listener.player = this.player;
        return listener;
    }
}
