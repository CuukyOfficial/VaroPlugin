package de.varoplugin.varo.game.world.secureable;

import de.varoplugin.varo.game.Varo;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class BlockAccessListener extends SecureableListener {

    public BlockAccessListener(Varo varo, VaroSecureable secureable) {
        super(varo, secureable);
    }

    @EventHandler
    public void onPlayerAccess(PlayerInteractEvent event) {
        if (!this.secureable.getBlock().equals(event.getClickedBlock())) return;
        event.setCancelled(true);
    }
}