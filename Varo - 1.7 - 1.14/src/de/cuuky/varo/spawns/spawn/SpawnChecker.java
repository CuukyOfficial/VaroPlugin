package de.cuuky.varo.spawns.spawn;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;

import de.cuuky.varo.version.types.Materials;

public class SpawnChecker {

	public static boolean checkSpawns(World world, int xPos, int zPos, float radius, int amount) {
		double alpha = (2 * Math.PI) / amount;
		Biome biome = new Location(world, xPos, world.getMaxHeight(), zPos).getBlock().getBiome();
		if(biome != Biome.PLAINS)
			return false;

		for(int count = 0; count != amount; count++) {
			double beta = alpha * count;

			double x = radius * Math.sin(beta);
			double z = radius * Math.cos(beta);

			int y = world.getMaxHeight();
			Material type;
			while(!(type = world.getBlockAt(xPos + (int) x, y, zPos + (int) z).getType()).isSolid()) {
				if(type == Material.WATER || type == Materials.WATER.parseMaterial() || type == Material.LAVA || type == Materials.LAVA.parseMaterial() || type.name().contains("LEAVES") || type.name().contains("WOOD"))
					return false;

				y--;
			}
		}

		return true;
	}
}
