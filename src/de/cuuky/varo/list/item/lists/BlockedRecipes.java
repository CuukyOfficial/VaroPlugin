package de.cuuky.varo.list.item.lists;

import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.list.item.ItemList;
import org.bukkit.inventory.ItemStack;

public class BlockedRecipes extends ItemList {

	public BlockedRecipes() {
		super("BlockedRecipes", -1, true);
	}
	
	@Override
	public void loadDefaultValues() {
		this.addItem(Materials.AIR.parseItem());
	}

	public boolean isBlocked(ItemStack itemstack) {
		itemstack = fixItem(itemstack);
		for (ItemStack stack : this.getItems())
			if (stack.equals(itemstack))
				return true;

		return false;
	}
}