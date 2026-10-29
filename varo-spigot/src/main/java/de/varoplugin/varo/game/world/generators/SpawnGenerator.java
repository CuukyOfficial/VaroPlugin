package de.varoplugin.varo.game.world.generators;

import org.bukkit.Location;

import com.cryptomorin.xseries.XMaterial;

import de.varoplugin.varo.Main;
import de.varoplugin.varo.player.VaroPlayer;
import de.varoplugin.varo.spawns.Spawn;
import de.varoplugin.varo.team.VaroTeam;
import de.varoplugin.varo.utils.BlockEditor;
import de.varoplugin.varo.utils.VaroUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class SpawnGenerator {

    private final XMaterial blockMaterial, sideBlockMaterial;
    private final List<CompletableFuture<Void>> pending = new ArrayList<>();

    private SpawnGenerator(XMaterial blockMaterial, XMaterial sideBlockMaterial) {
        this.blockMaterial = blockMaterial == null ? XMaterial.STONE_BRICK_SLAB : blockMaterial;
        this.sideBlockMaterial = sideBlockMaterial == null ? XMaterial.GRASS_BLOCK : sideBlockMaterial;

        for (Spawn spawn : Spawn.getSpawnsClone())
            spawn.delete();
    }

    public SpawnGenerator(Location location, int radius, int amount, XMaterial blockMaterial, XMaterial sideBlockId) {
        this(blockMaterial, sideBlockId);

        int i = 0;
        for (Location loc : generateSpawns(location, radius, amount)) {
            i++;
            Location newLoc = loc.clone();
            final int number = i;

            this.createSpawn(newLoc, spawnLocation -> new Spawn(number, spawnLocation));
        }
    }

    public SpawnGenerator(Location location, int radius, boolean withTeams, XMaterial blockMaterial, XMaterial sideBlockMaterial) {
        this(blockMaterial, sideBlockMaterial);

        Location[] locations = generateSpawns(location, radius, VaroPlayer.getAlivePlayer().size());
        Set<VaroPlayer> assigned = new HashSet<>();
        int i = 0;

        if (withTeams) {
            for (VaroTeam team : VaroTeam.getTeams()) {
                for (VaroPlayer player : team.getMember()) {
                    if (!player.getStats().isAlive() || i >= locations.length)
                        continue;

                    assigned.add(player);
                    this.createSpawn(locations[i], spawnLocation -> new Spawn(player, spawnLocation));
                    i++;
                }
            }
        }

        for (VaroPlayer player : VaroPlayer.getAlivePlayer()) {
            if (assigned.contains(player) || Spawn.getSpawn(player) != null || i >= locations.length)
                continue;

            this.createSpawn(locations[i], spawnLocation -> new Spawn(player, spawnLocation));
            i++;
        }
    }

    /**
     * Creates a spawn at the given location. Blocks can only be accessed by the thread owning the region on Folia, therefore this might happen
     * asynchronously. Use {@link #getFuture()} to wait for all spawns to be created.
     */
    private void createSpawn(Location location, Function<Location, Spawn> factory) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        this.pending.add(future);
        Main.scheduler().executeAt(location, () -> {
            try {
                BlockEditor editor = new BlockEditor(location.getWorld());
                factory.apply(this.setSpawnAt(location, editor));
                editor.apply(1).whenComplete((v, t) -> {
                    if (t != null)
                        future.completeExceptionally(t);
                    else
                        future.complete(null);
                });
            } catch (Throwable t) {
                future.completeExceptionally(t);
            }
        });
    }

    /**
     * @return Future that completes once all spawns have been created (immediately on all servers except Folia)
     */
    public CompletableFuture<Void> getFuture() {
        return CompletableFuture.allOf(this.pending.toArray(new CompletableFuture[0]));
    }

    private Location searchNext(Location location, int add, boolean needsAir) {
        Location newLocation = location.clone();
        while (VaroUtils.isNotSolidTerrainOrLiquid(newLocation.clone().add(0, add, 0).getBlock()) != needsAir)
            newLocation = newLocation.add(0, add, 0);

        if (add > 0)
            newLocation.add(0, 1, 0);

        return newLocation;
    }

    private Location setSpawnAt(Location location, BlockEditor editor) {
        final Location location0 = VaroUtils.isNotSolidTerrainOrLiquid(location.getBlock()) ? this.searchNext(location, -1, false) : this.searchNext(location, 1, true);
        final int x = location0.getBlockX(), y = location0.getBlockY(), z = location0.getBlockZ();

        editor.set(x, y, z, XMaterial.AIR);
        editor.set(x, y - 1, z, XMaterial.AIR);

        editor.set(x + 1, y, z, this.blockMaterial);
        editor.set(x, y, z + 1, this.blockMaterial);
        editor.set(x - 1, y, z, this.blockMaterial);
        editor.set(x, y, z - 1, this.blockMaterial);

        editor.set(x, y - 2, z, this.sideBlockMaterial);
        editor.set(x + 1, y - 1, z, this.sideBlockMaterial);
        editor.set(x, y - 1, z + 1, this.sideBlockMaterial);
        editor.set(x - 1, y - 1, z, this.sideBlockMaterial);
        editor.set(x, y - 1, z - 1, this.sideBlockMaterial);

        return location0.getBlock().getLocation().add(0.5, -1, 0.5);
    }

    private Location[] generateSpawns(Location middle, double radius, int amount) {
        double alpha = (2.0 * Math.PI) / amount;

        Location[] locations = new Location[amount];
        for (int i = 0; i < amount; i++) {
            double beta = alpha * i;

            double x = radius * Math.cos(beta);
            double z = radius * Math.sin(beta);

            locations[i] = middle.clone().add(x, middle.getWorld().getMaxHeight() - 1, z);
        }

        return locations;
    }
}