package de.cuuky.varo.world.generators;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import de.cuuky.varo.utils.BlockUtils;
import de.cuuky.varo.version.types.Materials;

public class PortalGenerator {

	public PortalGenerator(World world, int xPos, int zPos, int width, int height) {
		int yPos = world.getMaxHeight();
		while(BlockUtils.isAir(new Location(world, xPos, yPos, zPos).getBlock()))
			yPos--;

		int offset = width / 2;
		for(int y = 1; y < height - 1; y++) {
			for(int x = 1; x < width - 1; x++) {
				world.getBlockAt(xPos - offset + x, yPos + y, zPos).setType(Materials.NETHER_PORTAL.parseMaterial());
			}

			world.getBlockAt(xPos - offset, yPos + y, zPos).setType(Material.OBSIDIAN);
			world.getBlockAt(xPos - offset + width - 1, yPos + y, zPos).setType(Material.OBSIDIAN);
		}

		for(int x = 0; x < width; x++) {
			world.getBlockAt(xPos - offset + x, yPos, zPos).setType(Material.OBSIDIAN);
			world.getBlockAt(xPos - offset + x, yPos + height - 1, zPos).setType(Material.OBSIDIAN);
		}
	}
}
