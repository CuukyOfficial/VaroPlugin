package de.varoplugin.varo.game.world.generators;

import java.util.concurrent.CompletableFuture;

import org.bukkit.World;

import com.cryptomorin.xseries.XMaterial;

import de.varoplugin.varo.utils.BlockEditor;

public class PortalGenerator {

	private final CompletableFuture<Void> future;

	/**
	 * @param yPos The y coordinate of the ground the portal is placed on
	 */
	public PortalGenerator(World world, int xPos, int yPos, int zPos, int width, int height) {
		BlockEditor editor = new BlockEditor(world);
		int offset = width / 2;
		for (int y = 1; y < height - 1; y++) {
			for (int x = 1; x < width - 1; x++)
				editor.set(xPos - offset + x, yPos + y, zPos, XMaterial.NETHER_PORTAL, false);

			editor.set(xPos - offset, yPos + y, zPos, XMaterial.OBSIDIAN, false);
			editor.set(xPos - offset + width - 1, yPos + y, zPos, XMaterial.OBSIDIAN, false);
		}

		for (int x = 0; x < width; x++) {
			editor.set(xPos - offset + x, yPos, zPos, XMaterial.OBSIDIAN, false);
			editor.set(xPos - offset + x, yPos + height - 1, zPos, XMaterial.OBSIDIAN, false);
		}

		this.future = editor.apply(1);
	}

	/**
	 * @return Future that completes once the portal has been built
	 */
	public CompletableFuture<Void> getFuture() {
		return this.future;
	}
}
