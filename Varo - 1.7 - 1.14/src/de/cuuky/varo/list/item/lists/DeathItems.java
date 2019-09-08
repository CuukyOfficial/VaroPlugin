package de.cuuky.varo.list.item.lists;

import de.cuuky.varo.list.item.ItemList;
import de.cuuky.varo.version.types.Materials;

public class DeathItems extends ItemList {

	public DeathItems() {
		super("DeathItems");

		if(!items.isEmpty())
			return;

		items.add(Materials.AIR.parseItem());
	}
}
