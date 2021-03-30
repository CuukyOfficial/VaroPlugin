package de.cuuky.varo.game.world;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

import de.cuuky.cfw.utils.JavaUtils;
import de.cuuky.cfw.version.BukkitVersion;
import de.cuuky.cfw.version.VersionUtils;
import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.game.world.border.VaroWorldBorder;

public class VaroWorld {

	private World world;
	private VaroWorldBorder varoWorldBorder;

	public VaroWorld(World world) {
		this.world = world;

		if (ConfigSetting.BLOCK_ADVANCEMENTS.getValueAsBoolean()) {
			world.setGameRuleValue("announceAdvancements", "false");
			world.setGameRuleValue("announce-player-achievements", "false");
		}

		if (VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_7))
			this.varoWorldBorder = new VaroWorldBorder(world);
	}

	private List<Block> getBlocksBetweenPoints(Location l1, Location l2) {
		List<Block> blocks = new ArrayList<>();
		int topBlockX = (Math.max(l1.getBlockX(), l2.getBlockX()));
		int bottomBlockX = (Math.min(l1.getBlockX(), l2.getBlockX()));
		int topBlockY = (Math.max(l1.getBlockY(), l2.getBlockY()));
		int bottomBlockY = (Math.min(l1.getBlockY(), l2.getBlockY()));
		int topBlockZ = (Math.max(l1.getBlockZ(), l2.getBlockZ()));
		int bottomBlockZ = (Math.min(l1.getBlockZ(), l2.getBlockZ()));

		for (int x = bottomBlockX; x <= topBlockX; x++) {
			for (int y = bottomBlockY; y <= topBlockY; y++) {
				for (int z = bottomBlockZ; z <= topBlockZ; z++) {
					blocks.add(l1.getWorld().getBlockAt(x, y, z));
				}
			}
		}
		return blocks;
	}

	public void fillChests() {
		if (!ConfigSetting.RANDOM_CHEST_FILL_RADIUS.isIntActivated())
			return;

		int radius = ConfigSetting.RANDOM_CHEST_FILL_RADIUS.getValueAsInt();
		Location loc = world.getSpawnLocation().clone().add(radius, radius, radius);
		Location loc2 = world.getSpawnLocation().clone().add(-radius, -radius, -radius);

		int itemsPerChest = ConfigSetting.RANDOM_CHEST_MAX_ITEMS_PER_CHEST.getValueAsInt();
		ArrayList<ItemStack> chestItems = Main.getDataManager().getListManager().getChestItems().getItems();
		if (chestItems.isEmpty())
			return;

		for (Block block : getBlocksBetweenPoints(loc, loc2)) {
			if (!(block.getState() instanceof Chest))
				continue;

			Chest chest = (Chest) block.getState();
			chest.getBlockInventory().clear();
			for (int i = 0; i < itemsPerChest; i++) {
				int random = JavaUtils.randomInt(0, chest.getBlockInventory().getSize() - 1);
				while (chest.getBlockInventory().getContents().length != chest.getBlockInventory().getSize())
					random = JavaUtils.randomInt(0, chest.getBlockInventory().getSize() - 1);

				chest.getBlockInventory().setItem(random, chestItems.get(JavaUtils.randomInt(0, chestItems.size() - 1)));
			}
		}

		Bukkit.broadcastMessage("§7Alle Kisten um den " + Main.getColorCode() + "Spawn §7wurden " + Main.getColorCode() + "aufgefuellt§7!");
	}

	public World getWorld() {
		return this.world;
	}

	public VaroWorldBorder getVaroBorder() {
		return this.varoWorldBorder;
	}
}