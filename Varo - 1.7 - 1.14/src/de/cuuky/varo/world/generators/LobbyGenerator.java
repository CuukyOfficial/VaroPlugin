package de.cuuky.varo.world.generators;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.Location;

import de.cuuky.varo.Main;
import de.cuuky.varo.utils.BlockUtils;
import de.cuuky.varo.utils.JavaUtils;
import de.cuuky.varo.version.types.Materials;
import de.cuuky.varo.world.schematic.SchematicLoader;

@SuppressWarnings("deprecation")
public class LobbyGenerator {

	private static ArrayList<Materials> glassTypes;

	static {
		glassTypes = new ArrayList<>();

		glassTypes.add(Materials.ORANGE_STAINED_GLASS);
		glassTypes.add(Materials.MAGENTA_STAINED_GLASS);
		glassTypes.add(Materials.LIGHT_BLUE_STAINED_GLASS);
		glassTypes.add(Materials.YELLOW_STAINED_GLASS);
		glassTypes.add(Materials.LIME_STAINED_GLASS);
		glassTypes.add(Materials.PINK_STAINED_GLASS);
		glassTypes.add(Materials.CYAN_STAINED_GLASS);
		glassTypes.add(Materials.PURPLE_STAINED_GLASS);
	}

	private Location last;

	public LobbyGenerator(Location loc, File file) {
		try {
			new SchematicLoader(file).paste(loc);
		} catch (Error e) {
			System.out.println(Main.getConsolePrefix() + "Du brauchst WorldEdit, um den SchematicLoader zu nutzen!");
			return;
		}

		System.out.println(Main.getConsolePrefix() + "Autosetup: Loaded schematic " + file.getName());
	}

	public LobbyGenerator(Location curr, int height, int size) {
		makeWall(curr.clone().add(-5, -2, -5), size, 0, size);
		makeWall(curr, 0, height, -size);
		makeWall(curr, -size, -height, 0);
		makeWall(curr, 0, height, size);
		makeWall(curr, size, -height, 0);
	}

	private void makeWall(Location one, int x1, int y1, int z1) {
		Location from = last != null ? last : one;
		Location to = from.clone().add(x1, y1, z1);
		last = to;

		int topBlockX = (from.getBlockX() < to.getBlockX() ? to.getBlockX() : from.getBlockX());
		int bottomBlockX = (from.getBlockX() > to.getBlockX() ? to.getBlockX() : from.getBlockX());
		int topBlockY = (from.getBlockY() < to.getBlockY() ? to.getBlockY() : from.getBlockY());
		int bottomBlockY = (from.getBlockY() > to.getBlockY() ? to.getBlockY() : from.getBlockY());
		int topBlockZ = (from.getBlockZ() < to.getBlockZ() ? to.getBlockZ() : from.getBlockZ());
		int bottomBlockZ = (from.getBlockZ() > to.getBlockZ() ? to.getBlockZ() : from.getBlockZ());

		for (int x = bottomBlockX; x <= topBlockX; x++)
			for (int y = bottomBlockY; y <= topBlockY; y++)
				for (int z = bottomBlockZ; z <= topBlockZ; z++)
					BlockUtils.setBlock(to.getWorld().getBlockAt(x, y, z), glassTypes.get(JavaUtils.randomInt(0, glassTypes.size() - 1)));
	}
}