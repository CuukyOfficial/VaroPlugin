package de.varoplugin.varo.jobs.game.protectable;

import de.varoplugin.varo.game.world.protectable.VaroProtectable;
import de.varoplugin.varo.jobs.AbstractVaroListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockAccessTask extends AbstractVaroListener {

    private final VaroProtectable protectable;

    public BlockAccessTask(VaroProtectable protectable) {
        this.protectable = protectable;
    }

    @EventHandler
    public void onPlayerAccess(PlayerInteractEvent event) {
        if (!this.protectable.getBlock().equals(event.getClickedBlock())
            || this.protectable.getHolder().canAccessSavings(this.getVaro().getPlayer(event.getPlayer()))) return;
        event.setCancelled(true);
    }
}