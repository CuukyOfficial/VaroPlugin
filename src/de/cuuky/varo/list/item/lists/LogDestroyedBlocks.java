package de.cuuky.varo.list.item.lists;

import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.list.item.ItemList;

public class LogDestroyedBlocks extends ItemList {

	public LogDestroyedBlocks() {
		super("BlockLogger");
	}
	
	@Override
	public void loadDefaultValues() {
		this.items.add(Materials.DIAMOND_ORE.parseItem());
		this.items.add(Materials.LAPIS_ORE.parseItem());
		this.items.add(Materials.GOLD_ORE.parseItem());
	}

	public boolean shallLog(Block block) {
		for (ItemStack item : items)
			if (block.getType() == item.getType() && block.getData() == item.getData().getData())
				return true;

		return false;
	}
}