package de.cuuky.varo.list.item.lists;

import com.cryptomorin.xseries.XMaterial;

import de.cuuky.varo.list.item.ItemList;

public class DeathItems extends ItemList {

	public DeathItems() {
		super("DeathItems", -1, false);
	}

	@Override
	public void loadDefaultValues() {
		this.getItems().add(XMaterial.AIR.parseItem());
	}
}