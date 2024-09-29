package de.cuuky.varo.game.world.generators;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import com.cryptomorin.xseries.XBlock;
import com.cryptomorin.xseries.XMaterial;

import de.cuuky.varo.Main;
import de.cuuky.varo.utils.VaroUtils;

public class PortalGenerator {

	public PortalGenerator(World world, int xPos, int zPos, int width, int height) {
		int yPos = world.getMaxHeight();
		while (VaroUtils.isNotSolidTerrainOrLiquid(new Location(world, xPos, yPos, zPos).getBlock()))
			yPos--;

		final int yPos0 = yPos;
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
		    int offset = width / 2;
	        for (int y = 1; y < height - 1; y++) {
	            for (int x = 1; x < width - 1; x++)
	                XBlock.setType(world.getBlockAt(xPos - offset + x, yPos0 + y, zPos), XMaterial.NETHER_PORTAL, false);

	            XBlock.setType(world.getBlockAt(xPos - offset, yPos0 + y, zPos), XMaterial.OBSIDIAN);
	            XBlock.setType(world.getBlockAt(xPos - offset + width - 1, yPos0 + y, zPos), XMaterial.OBSIDIAN);
	        }

	        for (int x = 0; x < width; x++) {
	            XBlock.setType(world.getBlockAt(xPos - offset + x, yPos0, zPos), XMaterial.OBSIDIAN);
	            XBlock.setType(world.getBlockAt(xPos - offset + x, yPos0 + height - 1, zPos), XMaterial.OBSIDIAN);
	        }
		}, 1);
	}
}