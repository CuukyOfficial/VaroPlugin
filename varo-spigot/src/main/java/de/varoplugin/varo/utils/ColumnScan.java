package de.varoplugin.varo.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import org.bukkit.Location;
import org.bukkit.World;

import de.varoplugin.varo.Main;

/**
 * Computes a value for a set of block columns. Columns are grouped by chunk and every chunk is processed by the thread owning it (required on
 * Folia). On all other servers everything is computed immediately on the calling thread.
 */
public class ColumnScan {

    /**
     * Returned by a {@link Function} to abort the scan
     */
    public static final int ABORT = Integer.MIN_VALUE;

    @FunctionalInterface
    public interface Function {

        int apply(World world, int x, int z);
    }

    private final Map<Long, Integer> values;
    private final boolean aborted;

    private ColumnScan(Map<Long, Integer> values, boolean aborted) {
        this.values = values;
        this.aborted = aborted;
    }

    public boolean isAborted() {
        return this.aborted;
    }

    public int get(int x, int z) {
        return this.values.get(key(x, z));
    }

    private static long key(int x, int z) {
        return ((long) x << 32) | (z & 0xFFFFFFFFL);
    }

    /**
     * @return All columns within a circle
     */
    public static List<int[]> disc(int centerX, int centerZ, int radius) {
        List<int[]> columns = new ArrayList<>();
        for (int i = -radius; i <= radius; i++)
            for (int j = -radius; j <= radius; j++)
                if (i * i + j * j <= radius * radius)
                    columns.add(new int[] { centerX + i, centerZ + j });
        return columns;
    }

    /**
     * @param columns Pairs of x and z
     */
    public static CompletableFuture<ColumnScan> scan(World world, Collection<int[]> columns, Function function) {
        Map<Long, List<int[]>> chunks = new LinkedHashMap<>();
        for (int[] column : columns)
            chunks.computeIfAbsent(((long) (column[0] >> 4) << 32) | ((column[1] >> 4) & 0xFFFFFFFFL), k -> new ArrayList<>()).add(column);

        Map<Long, Integer> values = new ConcurrentHashMap<>();
        AtomicBoolean aborted = new AtomicBoolean();
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (List<int[]> chunk : chunks.values()) {
            CompletableFuture<Void> future = new CompletableFuture<>();
            futures.add(future);
            Main.scheduler().executeAt(new Location(world, chunk.get(0)[0], 0, chunk.get(0)[1]), () -> {
                try {
                    for (int[] column : chunk) {
                        if (aborted.get())
                            break;

                        int value = function.apply(world, column[0], column[1]);
                        if (value == ABORT) {
                            aborted.set(true);
                            break;
                        }
                        values.put(key(column[0], column[1]), value);
                    }
                    future.complete(null);
                } catch (Throwable t) {
                    future.completeExceptionally(t);
                }
            });
        }
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).thenApply(v -> new ColumnScan(values, aborted.get()));
    }
}
