package de.cuuky.varo.list.item.lists;

import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.list.item.ItemList;

public class ChestItems extends ItemList {

	public ChestItems() {
		super("ChestItems", -1, true);
	}
	
	@Override
	public void loadDefaultValues() {
		this.addItem(Materials.WOODEN_AXE.parseItem());
		this.addItem(Materials.SUGAR_CANE.parseItem());
		this.addItem(Materials.APPLE.parseItem());
		this.addItem(Materials.PORKCHOP.parseItem());
		this.addItem(Materials.OAK_WOOD.parseItem());
		this.addItem(Materials.STICK.parseItem());
		this.addItem(Materials.COBBLESTONE.parseItem());
		this.addItem(Materials.SAND.parseItem());
	}
}