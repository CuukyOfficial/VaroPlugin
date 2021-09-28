package de.cuuky.varo.list.item.lists;

import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.list.item.ItemList;

public class DeathItems extends ItemList {

	public DeathItems() {
		super("DeathItems", -1, false);
	}

	@Override
	public void loadDefaultValues() {
		this.items.add(Materials.AIR.parseItem());
	}
}