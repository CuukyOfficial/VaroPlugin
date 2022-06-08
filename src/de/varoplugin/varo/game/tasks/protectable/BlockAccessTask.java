package de.varoplugin.varo.game.tasks.protectable;

import de.varoplugin.varo.game.world.protectable.VaroProtectable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class BlockAccessTask extends SecureableTask {

    public BlockAccessTask(VaroProtectable secureable) {
        super(secureable);
    }

    @EventHandler
    public void onPlayerAccess(PlayerInteractEvent event) {
        if (!this.secureable.getBlock().equals(event.getClickedBlock())
            || this.secureable.getHolder().canAccessSavings(this.varo.getPlayer(event.getPlayer()))) return;
        event.setCancelled(true);
    }
}