package de.cuuky.varo.heartbeat.listener;

import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.heartbeat.PlayerListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;
import java.util.function.Predicate;

// TODO: everything
public class NoBuildListener extends PlayerListener {

    public NoBuildListener(VaroPlayer player, List<Predicate<VaroPlayer>> checks) {
        super(player, checks);
    }

    @EventHandler
    public void onPlayerBuild(BlockBreakEvent event) {
        if (!this.isActivated())
            return;

        event.setCancelled(true);
    }
}