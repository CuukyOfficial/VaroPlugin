package de.cuuky.varo.list.item.lists;

import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.list.item.ItemList;

public class ChestItems extends ItemList {

	public ChestItems() {
		super("ChestItems", -1, true);
	}
	
	@Override
	public void loadDefaultValues() {
		this.items.add(Materials.WOODEN_AXE.parseItem());
		this.items.add(Materials.SUGAR_CANE.parseItem());
		this.items.add(Materials.APPLE.parseItem());
		this.items.add(Materials.PORKCHOP.parseItem());
		this.items.add(Materials.OAK_WOOD.parseItem());
		this.items.add(Materials.STICK.parseItem());
		this.items.add(Materials.COBBLESTONE.parseItem());
		this.items.add(Materials.SAND.parseItem());
	}
}