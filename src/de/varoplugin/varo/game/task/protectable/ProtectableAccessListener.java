package de.varoplugin.varo.game.task.protectable;

import de.varoplugin.varo.game.world.protectable.Protectable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

public class ProtectableAccessListener extends AbstractProtectableListener{

    public ProtectableAccessListener(Protectable protectable) {
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
