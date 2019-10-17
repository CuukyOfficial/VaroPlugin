package de.cuuky.varo.list.item.lists;

import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.list.item.ItemList;
import de.cuuky.varo.version.types.Materials;

public class LogDestroyedBlocks extends ItemList {

	@SuppressWarnings("deprecation")
	public LogDestroyedBlocks() {
		super("BlockLogger");

		if (!items.isEmpty())
			return;

		items.add(Materials.DIAMOND_ORE.parseItem());
		items.add(Materials.LAPIS_ORE.parseItem());
		items.add(Materials.GOLD_ORE.parseItem());
	}

	public boolean shallLog(Block block) {
		for (ItemStack item : items)
			if (block.getType() == item.getType() && block.getData() == item.getData().getData())
				return true;

		return false;
	}
}
