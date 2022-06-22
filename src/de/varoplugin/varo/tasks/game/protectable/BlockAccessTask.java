package de.varoplugin.varo.tasks.game.protectable;

import de.varoplugin.varo.game.world.protectable.VaroProtectable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockAccessTask extends ProtectableTask {

    public BlockAccessTask(VaroProtectable secureable) {
        super(secureable);
    }

    @EventHandler
    public void onPlayerAccess(PlayerInteractEvent event) {
        if (!this.secureable.getBlock().equals(event.getClickedBlock())
            || this.secureable.getHolder().canAccessSavings(this.getInfo().getVaro().getPlayer(event.getPlayer()))) return;
        event.setCancelled(true);
    }
}