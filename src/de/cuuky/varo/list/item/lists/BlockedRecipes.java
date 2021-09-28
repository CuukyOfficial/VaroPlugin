package de.cuuky.varo.list.item.lists;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.list.item.ItemList;

public class BlockedRecipes extends ItemList {

	public BlockedRecipes() {
		super("BlockedRecipes", -1, true);
	}
	
	@Override
	public void loadDefaultValues() {
		this.items.add(new ItemStack(Material.AIR));
	}

	public boolean isBlocked(ItemStack itemstack) {
		itemstack = fixItem(itemstack);
		for (ItemStack stack : items)
			if (stack.equals(itemstack))
				return true;

		return false;
	}
}