package de.cuuky.varo.list.item.lists;

import de.cuuky.varo.list.item.ItemList;
import de.cuuky.varo.version.types.Materials;

public class ChestItems extends ItemList {

	@SuppressWarnings("deprecation")
	public ChestItems() {
		super("ChestItems");

		if (!items.isEmpty())
			return;

		items.add(Materials.WOODEN_AXE.parseItem());
		items.add(Materials.SUGAR_CANE.parseItem());
		items.add(Materials.APPLE.parseItem());
		items.add(Materials.PORKCHOP.parseItem());
		items.add(Materials.OAK_WOOD.parseItem());
		items.add(Materials.STICK.parseItem());
		items.add(Materials.COBBLESTONE.parseItem());
		items.add(Materials.SAND.parseItem());
	}
}
