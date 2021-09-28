package de.cuuky.varo.list.item.lists;

import org.bukkit.inventory.ItemStack;

import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.list.item.ItemList;

public class BlockedItems extends ItemList {

	public BlockedItems() {
		super("BlockedItems", -1, true);
	}
	
	@Override
	public void loadDefaultValues() {
		this.items.add(Materials.AIR.parseItem());
	}

	public boolean isBlocked(ItemStack itemstack) {
		itemstack = fixItem(itemstack);
		for (ItemStack stack : items)
			if (stack.equals(itemstack))
				return true;

		return false;
	}
}