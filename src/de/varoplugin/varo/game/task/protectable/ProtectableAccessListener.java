package de.varoplugin.varo.game.task.protectable;

import de.varoplugin.varo.game.world.protectable.VaroProtectable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

public class ProtectableAccessListener extends AbstractProtectableListener{

    public ProtectableAccessListener(VaroProtectable protectable) {
        super(protectable);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) return;
        if (!this.getProtectable().getBlock().equals(event.getClickedBlock())) return;
        if (this.getProtectable().getHolder().canAccessSavings(this.getVaro().getPlayer(event.getPlayer()))) return;

        event.setCancelled(true);
    }
}
