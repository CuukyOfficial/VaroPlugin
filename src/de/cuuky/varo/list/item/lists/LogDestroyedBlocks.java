package de.cuuky.varo.list.item.lists;

import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.list.item.ItemList;

public class LogDestroyedBlocks extends ItemList {

	public LogDestroyedBlocks() {
		super("BlockLogger", -1, true, false);
	}
	
	@Override
	public void loadDefaultValues() {
		this.addItem(Materials.DIAMOND_ORE.parseItem());
		this.addItem(Materials.LAPIS_ORE.parseItem());
		this.addItem(Materials.GOLD_ORE.parseItem());
	}

	public boolean shallLog(Block block) {
		for (ItemStack item : this.getItems())
			if (block.getType() == item.getType() && block.getData() == item.getData().getData())
				return true;

		return false;
	}
}