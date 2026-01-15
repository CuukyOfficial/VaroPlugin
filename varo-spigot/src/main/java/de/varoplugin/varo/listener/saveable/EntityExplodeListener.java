package de.varoplugin.varo.listener.saveable;

import com.cryptomorin.xseries.XMaterial;
import de.varoplugin.varo.Main;
import de.varoplugin.varo.player.stats.stat.inventory.VaroSaveable;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EntityExplodeListener implements Listener {

    private static boolean chestNearby(Location location) {
        for (int x = location.getBlockX() - 1; x <= location.getBlockX() + 1; x++) {
            for (int y = location.getBlockY() - 1; y <= location.getBlockY() + 1; y++) {
                for (int z = location.getBlockZ() - 1; z <= location.getBlockZ() + 1; z++) {
                    Location loc = new Location(location.getWorld(), x, y, z);
                    if (VaroSaveable.getByLocation(loc) != null)
                        return true;
                }
            }
        }
        return false;
    }

    private static void handleExplosion(@NotNull Cancellable event, @NotNull List<Block> blockList) {
        if (!Main.getVaroGame().hasStarted()) {
            event.setCancelled(true);
            return;
        }

        blockList.removeIf(block -> ((block.getType() == XMaterial.CHEST.get() || block.getType() == XMaterial.FURNACE.get())
                && VaroSaveable.getByLocation(block.getLocation()) != null) || (block.getType().name().contains("SIGN") && chestNearby(block.getLocation())));
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        handleExplosion(event, event.blockList());
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {
        handleExplosion(event, event.blockList());
    }
}