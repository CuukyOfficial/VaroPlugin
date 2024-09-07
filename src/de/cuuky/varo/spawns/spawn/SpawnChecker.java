package de.cuuky.varo.spawns.spawn;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;

import com.cryptomorin.xseries.XMaterial;

import de.cuuky.varo.configuration.configurations.config.ConfigSetting;

public class SpawnChecker {

	public static boolean checkSpawns(World world, int xPos, int zPos, float radius, int amount) {
		double alpha = (2 * Math.PI) / amount;
		Biome biome = new Location(world, xPos, world.getMaxHeight(), zPos).getBlock().getBiome();
		if (biome != Biome.PLAINS)
			return false;

		int tolerance = ConfigSetting.WORLD_SPAWNS_GENERATE_Y_TOLERANCE.getValueAsInt();
		int firstY = -1;
		for (int count = 0; count != amount; count++) {
			double beta = alpha * count;

			double x = radius * Math.sin(beta);
			double z = radius * Math.cos(beta);

			int y = world.getMaxHeight();
			Material type;
			while (!(type = world.getBlockAt(xPos + (int) x, y, zPos + (int) z).getType()).isSolid()) {
				if (type == XMaterial.WATER.parseMaterial()  || type == XMaterial.LAVA.parseMaterial() || type.name().contains("LEAVES") || type.name().contains("WOOD"))
					return false;

				y--;
			}

			if (firstY == -1)
				firstY = y;
			else if ((firstY > y ? firstY - y : y - firstY) > tolerance)
				return false;
		}

		return true;
	}
}