package de.varoplugin.varo.spawns.spawn;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;

import com.cryptomorin.xseries.XMaterial;

import de.varoplugin.varo.Main;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;
import de.varoplugin.varo.utils.ColumnScan;

public class SpawnChecker {

	/**
	 * Checks whether spawns can be placed around the given position. Blocks are read by the threads owning them, so the result might be
	 * available asynchronously on Folia.
	 */
	public static CompletableFuture<Boolean> checkSpawns(World world, int xPos, int zPos, float radius, int amount) {
		CompletableFuture<Boolean> result = new CompletableFuture<>();
		Main.scheduler().executeAt(new Location(world, xPos, 0, zPos), () -> {
			try {
				Biome biome = new Location(world, xPos, world.getMaxHeight(), zPos).getBlock().getBiome();
				if (biome != Biome.PLAINS) {
					result.complete(false);
					return;
				}

				double alpha = (2 * Math.PI) / amount;
				List<int[]> columns = new ArrayList<>();
				for (int count = 0; count != amount; count++) {
					double beta = alpha * count;
					columns.add(new int[] { xPos + (int) (radius * Math.sin(beta)), zPos + (int) (radius * Math.cos(beta)) });
				}

				ColumnScan.scan(world, columns, SpawnChecker::surface).whenComplete((scan, t) -> {
					if (t != null)
						result.completeExceptionally(t);
					else
						result.complete(checkTolerance(scan, columns));
				});
			} catch (Throwable t) {
				result.completeExceptionally(t);
			}
		});
		return result;
	}

	private static int surface(World world, int x, int z) {
		int y = world.getMaxHeight();
		Material type;
		while (!(type = world.getBlockAt(x, y, z).getType()).isSolid()) {
			if (type == XMaterial.WATER.get() || type == XMaterial.LAVA.get() || type.name().contains("LEAVES") || type.name().contains("WOOD"))
				return ColumnScan.ABORT;

			y--;
		}
		return y;
	}

	private static boolean checkTolerance(ColumnScan scan, List<int[]> columns) {
		if (scan.isAborted())
			return false;

		int tolerance = ConfigSetting.WORLD_SPAWNS_GENERATE_Y_TOLERANCE.getValueAsInt();
		int firstY = -1;
		for (int[] column : columns) {
			int y = scan.get(column[0], column[1]);
			if (firstY == -1)
				firstY = y;
			else if (Math.abs(firstY - y) > tolerance)
				return false;
		}
		return true;
	}
}
