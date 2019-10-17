package de.cuuky.varo.list.item.lists;

import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.list.item.ItemList;
import de.cuuky.varo.version.types.Materials;

public class BlockedItems extends ItemList {

	@SuppressWarnings("deprecation")
	public BlockedItems() {
		super("BlockedItems");

		if (!items.isEmpty())
			return;

		items.add(Materials.AIR.parseItem());
	}

	@SuppressWarnings("deprecation")
	public boolean isBlocked(ItemStack itemstack) {
		itemstack = fixItem(itemstack);
		for (ItemStack stack : items)
			if (stack.equals(itemstack))
				return true;

		return false;
	}
}
