package de.cuuky.varo.game.world.generators;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.cryptomorin.xseries.XBlock;
import com.cryptomorin.xseries.XMaterial;

import de.cuuky.varo.Main;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.team.VaroTeam;
import de.cuuky.varo.spawns.Spawn;
import de.cuuky.varo.utils.VaroUtils;

public class SpawnGenerator {

    private final XMaterial blockMaterial, sideBlockMaterial;

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

            new Spawn(i, setSpawnAt(newLoc));
        }
    }

    public SpawnGenerator(Location location, int radius, boolean withTeams, XMaterial blockMaterial, XMaterial sideBlockMaterial) {
        this(blockMaterial, sideBlockMaterial);

        Location[] locations = generateSpawns(location, radius, VaroPlayer.getAlivePlayer().size());
        int i = 0;

        if (withTeams) {
            for (VaroTeam team : VaroTeam.getTeams()) {
                for (VaroPlayer player : team.getMember()) {
                    if (!player.getStats().isAlive() || i >= locations.length)
                        continue;

                    new Spawn(player, this.setSpawnAt(locations[i]));
                    i++;
                }
            }
        }

        for (VaroPlayer player : VaroPlayer.getAlivePlayer()) {
            if (Spawn.getSpawn(player) != null || i >= locations.length)
                continue;

            new Spawn(player, this.setSpawnAt(locations[i]));
            i++;
        }
    }

    private Location searchNext(Location location, int add, boolean needsAir) {
        Location newLocation = location.clone();
        while (VaroUtils.isNotSolidTerrain(newLocation.clone().add(0, add, 0).getBlock()) != needsAir)
            newLocation = newLocation.add(0, add, 0);

        if (add > 0)
            newLocation.add(0, 1, 0);

        return newLocation;
    }

    private Location setSpawnAt(Location location) {
        final Location location0 = VaroUtils.isNotSolidTerrain(location.getBlock()) ? this.searchNext(location, -1, false) : this.searchNext(location, 1, true);

        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
            XBlock.setType(location0.clone().add(0, -1, 0).getBlock(), XMaterial.AIR);
            
            XBlock.setType(location0.clone().add(1, 0, 0).getBlock(), blockMaterial);
            XBlock.setType(location0.clone().add(0, 0, 1).getBlock(), blockMaterial);
            XBlock.setType(location0.clone().add(-1, 0, 0).getBlock(), blockMaterial);
            XBlock.setType(location0.clone().add(0, 0, -1).getBlock(), blockMaterial);

            XBlock.setType(location0.clone().add(0, -2, 0).getBlock(), sideBlockMaterial);
            XBlock.setType(location0.clone().add(1, -1, 0).getBlock(), sideBlockMaterial);
            XBlock.setType(location0.clone().add(0, -1, 1).getBlock(), sideBlockMaterial);
            XBlock.setType(location0.clone().add(-1, -1, 0).getBlock(), sideBlockMaterial);
            XBlock.setType(location0.clone().add(0, -1, -1).getBlock(), sideBlockMaterial);
        }, 1);

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