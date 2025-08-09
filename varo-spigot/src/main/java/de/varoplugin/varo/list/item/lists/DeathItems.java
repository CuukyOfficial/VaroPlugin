package de.varoplugin.varo.list.item.lists;

import com.cryptomorin.xseries.XMaterial;

import de.varoplugin.varo.list.item.ItemList;

public class DeathItems extends ItemList {

	public DeathItems() {
		super("DeathItems", -1, false);
	}

	@Override
	public void loadDefaultValues() {
		this.getItems().add(XMaterial.AIR.parseItem());
	}
}