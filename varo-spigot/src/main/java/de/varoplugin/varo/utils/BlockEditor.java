package de.varoplugin.varo.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.bukkit.Location;
import org.bukkit.World;

import com.cryptomorin.xseries.XBlock;
import com.cryptomorin.xseries.XMaterial;

import de.varoplugin.varo.Main;

/**
 * Collects block changes and applies them grouped by chunk. Every chunk is modified by the thread owning it, which is required on Folia where
 * a structure may span multiple regions. On all other servers the changes are applied on the main thread.
 */
public class BlockEditor {

    private final World world;
    private final Map<Long, List<Runnable>> changes = new LinkedHashMap<>();

    public BlockEditor(World world) {
        this.world = world;
    }

    public BlockEditor set(int x, int y, int z, XMaterial material) {
        return this.add(x, z, () -> XBlock.setType(this.world.getBlockAt(x, y, z), material));
    }

    public BlockEditor set(int x, int y, int z, XMaterial material, boolean applyPhysics) {
        return this.add(x, z, () -> XBlock.setType(this.world.getBlockAt(x, y, z), material, applyPhysics));
    }

    public BlockEditor set(Location location, XMaterial material) {
        return this.set(location.getBlockX(), location.getBlockY(), location.getBlockZ(), material);
    }

    private BlockEditor add(int x, int z, Runnable change) {
        long key = ((long) (x >> 4) << 32) | ((z >> 4) & 0xFFFFFFFFL);
        this.changes.computeIfAbsent(key, k -> new ArrayList<>()).add(change);
        return this;
    }

    /**
     * Applies all collected changes.
     *
     * @param delay Delay in ticks before the changes are applied
     * @return Future that completes once all changes have been applied
     */
    public CompletableFuture<Void> apply(long delay) {
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (Map.Entry<Long, List<Runnable>> entry : this.changes.entrySet()) {
            CompletableFuture<Void> future = new CompletableFuture<>();
            futures.add(future);

            int chunkX = (int) (entry.getKey() >> 32), chunkZ = entry.getKey().intValue();
            Location chunk = new Location(this.world, chunkX << 4, 0, chunkZ << 4);
            List<Runnable> list = entry.getValue();
            Runnable task = () -> {
                try {
                    list.forEach(Runnable::run);
                    future.complete(null);
                } catch (Throwable t) {
                    future.completeExceptionally(t);
                }
            };

            if (delay > 0 && Main.scheduler().isRegionized())
                Main.scheduler().runAtLater(chunk, task, delay);
            else
                Main.scheduler().executeAt(chunk, task);
        }
        this.changes.clear();
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
    }

    public CompletableFuture<Void> apply() {
        return this.apply(0);
    }
}
