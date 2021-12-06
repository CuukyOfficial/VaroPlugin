package de.cuuky.varo.game.world.generators;

import org.bukkit.Location;
import org.bukkit.World;

import de.cuuky.cfw.utils.BlockUtils;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.Main;

public class PortalGenerator {

	public PortalGenerator(World world, int xPos, int zPos, int width, int height) {
		int yPos = world.getMaxHeight();
		while (BlockUtils.isAir(new Location(world, xPos, yPos, zPos).getBlock()))
			yPos--;

		int offset = width / 2;
		for (int y = 1; y < height - 1; y++) {
			for (int x = 1; x < width - 1; x++) {
				BlockUtils.setBlockDelayed(Main.getInstance(), world, xPos - offset + x, yPos + y, zPos, Materials.NETHER_PORTAL);
			}

			BlockUtils.setBlockDelayed(Main.getInstance(), world, xPos - offset, yPos + y, zPos, Materials.OBSIDIAN);
			BlockUtils.setBlockDelayed(Main.getInstance(), world, xPos - offset + width - 1, yPos + y, zPos, Materials.OBSIDIAN);
		}

		for (int x = 0; x < width; x++) {
			BlockUtils.setBlockDelayed(Main.getInstance(), world, xPos - offset + x, yPos, zPos, Materials.OBSIDIAN);
			BlockUtils.setBlockDelayed(Main.getInstance(), world, xPos - offset + x, yPos + height - 1, zPos, Materials.OBSIDIAN);
		}
	}
}