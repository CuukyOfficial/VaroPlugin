package de.cuuky.varo.list.item.lists;

import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.list.item.ItemList;

public class ChestItems extends ItemList {

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