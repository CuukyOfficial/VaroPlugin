package de.cuuky.varo.game.world.generators;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

import com.cryptomorin.xseries.XBlock;
import com.cryptomorin.xseries.XMaterial;

import de.cuuky.varo.Main;
import de.cuuky.varo.game.world.schematic.SchematicLoader;
import de.varoplugin.cfw.utils.JavaUtils;

public class LobbyGenerator {

	private static List<XMaterial> glassTypes;

	static {
		glassTypes = new ArrayList<>();

		glassTypes.add(XMaterial.ORANGE_STAINED_GLASS);
		glassTypes.add(XMaterial.MAGENTA_STAINED_GLASS);
		glassTypes.add(XMaterial.LIGHT_BLUE_STAINED_GLASS);
		glassTypes.add(XMaterial.YELLOW_STAINED_GLASS);
		glassTypes.add(XMaterial.LIME_STAINED_GLASS);
		glassTypes.add(XMaterial.PINK_STAINED_GLASS);
		glassTypes.add(XMaterial.CYAN_STAINED_GLASS);
		glassTypes.add(XMaterial.PURPLE_STAINED_GLASS);
	}

	private Location last;

	public LobbyGenerator(Location loc, File file) {
	    SchematicLoader schematicLoader = SchematicLoader.getInstance();
		if (schematicLoader == null) {
		    System.out.println(Main.getConsolePrefix() + "Du brauchst WorldEdit, um den SchematicLoader zu nutzen!");
		    return;
		}
		schematicLoader.paste(file, loc);
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
				    XBlock.setType(to.getWorld().getBlockAt(x, y, z), glassTypes.get(JavaUtils.randomInt(0, glassTypes.size() - 1)), true);
		
	}
}