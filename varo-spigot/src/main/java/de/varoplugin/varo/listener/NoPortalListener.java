package de.varoplugin.varo.listener;

import com.cryptomorin.xseries.XMaterial;
import de.varoplugin.cfw.version.ServerVersion;
import de.varoplugin.cfw.version.VersionUtils;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;
import org.apache.commons.collections4.map.PassiveExpiringMap;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class NoPortalListener implements Listener {

    private static final MethodHandle GET_BLOCKS;

    static {
        try {
            // PortalCreateEvent#getBlocks was changed from ArrayList<Block> to List<BlockState> in 1.14
            MethodType type = VersionUtils.getVersion().isLowerThan(ServerVersion.ONE_14) ? MethodType.methodType(ArrayList.class) : MethodType.methodType(List.class);
            GET_BLOCKS = MethodHandles.lookup().findVirtual(PortalCreateEvent.class, "getBlocks", type);
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static final int RADIUS = 1;

    private final Set<Location> allowedPortals = Collections.newSetFromMap(new PassiveExpiringMap<>(2, TimeUnit.SECONDS));

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!ConfigSetting.BLOCK_USER_PORTALS.getValueAsBoolean()
                || event.getPlayer().hasPermission("varo.portals")
                || !event.getBlock().getType().equals(Material.OBSIDIAN)
                || !netherPortalNearby(event.getBlock().getLocation(), RADIUS))
            return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (!ConfigSetting.BLOCK_USER_PORTALS.getValueAsBoolean()
                || event.getAction() != Action.RIGHT_CLICK_BLOCK
                || event.getClickedBlock() == null
                || event.getClickedBlock().getType() != XMaterial.OBSIDIAN.get()
                || event.getPlayer().getItemInHand() == null)
            return;

        if (event.getPlayer().hasPermission("varo.portals")
                && event.getPlayer().getItemInHand().getType() == XMaterial.FLINT_AND_STEEL.get()) {
            this.allowedPortals.isEmpty(); // Removes all expired entries
            this.allowedPortals.add(event.getClickedBlock().getRelative(event.getBlockFace()).getLocation());
        }
    }

    @EventHandler
    public void onPortalCreate(PortalCreateEvent event) throws Throwable {
        if (!ConfigSetting.BLOCK_USER_PORTALS.getValueAsBoolean() || event.getReason() != PortalCreateEvent.CreateReason.FIRE)
            return;

        for (Object o : (List<?>) GET_BLOCKS.invoke(event)) {
            Location location = o instanceof Block ? ((Block) o).getLocation() : ((BlockState) o).getLocation();
            if (this.allowedPortals.contains(location))
                return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
        if (ConfigSetting.BLOCK_USER_PORTALS.getValueAsBoolean()
                && event.getBlockClicked() != null
                && netherPortalNearby(event.getBlockClicked().getLocation(), RADIUS))
            event.setCancelled(true);
    }

    private void handleExplosionEvent(@NotNull List<Block> blocks) {
        if (!ConfigSetting.BLOCK_USER_PORTALS.getValueAsBoolean())
            return;

        blocks.removeIf(block -> block.getType() == XMaterial.NETHER_PORTAL.get());
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        this.handleExplosionEvent(event.blockList());
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {
        this.handleExplosionEvent(event.blockList());
    }

    private void handlePistonEvent(@NotNull Cancellable event, @NotNull List<Block> blocks) {
        if (!ConfigSetting.BLOCK_USER_PORTALS.getValueAsBoolean() || event.isCancelled())
            return;

        for (Block block : blocks)
            if (block.getType() == XMaterial.NETHER_PORTAL.get()
                    || (block.getType() == XMaterial.OBSIDIAN.get() && netherPortalNearby(block.getLocation(), RADIUS))) {
                event.setCancelled(true);
                return;
            }
    }

    @EventHandler
    public void onPistonExtend(BlockPistonExtendEvent event) {
        this.handlePistonEvent(event, event.getBlocks());
    }

    @EventHandler
    public void onPistonRetract(BlockPistonRetractEvent event) {
        this.handlePistonEvent(event, event.getBlocks());
    }

    private static boolean netherPortalNearby(Location location, int radius) {
        for (int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for (int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                for (int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                    if (location.getWorld().getBlockAt(x, y, z).getType() == XMaterial.NETHER_PORTAL.get())
                        return true;
                }
            }
        }
        return false;
    }
}
