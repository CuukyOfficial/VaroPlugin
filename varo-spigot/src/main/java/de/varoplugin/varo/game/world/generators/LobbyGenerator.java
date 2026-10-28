package de.varoplugin.varo.game.world.generators;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

import org.bukkit.Location;

import com.cryptomorin.xseries.XMaterial;

import de.varoplugin.cfw.utils.JavaUtils;
import de.varoplugin.varo.Main;
import de.varoplugin.varo.game.world.schematic.SchematicLoader;
import de.varoplugin.varo.utils.BlockEditor;

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
	private CompletableFuture<Void> future = CompletableFuture.completedFuture(null);

	public LobbyGenerator(Location loc, File file) {
	    SchematicLoader schematicLoader = SchematicLoader.getInstance();
		if (schematicLoader == null) {
		    Main.getInstance().getLogger().log(Level.SEVERE, "Worldedit is required to load schematics!");
		    return;
		}
		if (Main.scheduler().isRegionized())
		    Main.getInstance().getLogger().log(Level.WARNING, "Pasting schematics requires WorldEdit to support Folia");

		this.future = new CompletableFuture<>();
		Main.scheduler().executeAt(loc, () -> {
			try {
				schematicLoader.paste(file, loc);
				Main.getInstance().getLogger().log(Level.INFO, "Autosetup: Loaded schematic " + file.getName());
				this.future.complete(null);
			} catch (Throwable t) {
				this.future.completeExceptionally(t);
			}
		});
	}

	public LobbyGenerator(Location curr, int height, int size) {
		// Blocks are grouped by chunk, as they can only be modified by the thread owning the region on Folia
		BlockEditor editor = new BlockEditor(curr.getWorld());
		this.makeWall(editor, curr.clone().add(-5, -2, -5), size, 0, size);
		this.makeWall(editor, curr, 0, height, -size);
		this.makeWall(editor, curr, -size, -height, 0);
		this.makeWall(editor, curr, 0, height, size);
		this.makeWall(editor, curr, size, -height, 0);
		this.future = editor.apply();
	}

	/**
	 * @return Future that completes once the lobby has been built
	 */
	public CompletableFuture<Void> getFuture() {
		return this.future;
	}

	private void makeWall(BlockEditor editor, Location one, int x1, int y1, int z1) {
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
				    editor.set(x, y, z, glassTypes.get(JavaUtils.randomInt(0, glassTypes.size() - 1)), true);
	}
}
