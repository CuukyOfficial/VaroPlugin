package de.cuuky.varo.heartbeat.listener;

import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.heartbeat.PlayerListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.List;
import java.util.function.Predicate;

// TODO: everything
public class NoMoveListener extends PlayerListener {

    public NoMoveListener(VaroPlayer player, List<Predicate<VaroPlayer>> checks) {
        super(player, checks);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!this.isActivated())
            return;

        event.setCancelled(true);
    }
}
