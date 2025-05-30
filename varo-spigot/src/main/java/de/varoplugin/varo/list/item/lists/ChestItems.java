package de.varoplugin.varo.list.item.lists;

import com.cryptomorin.xseries.XMaterial;

import de.varoplugin.varo.list.item.ItemList;

public class ChestItems extends ItemList {

	public ChestItems() {
		super("ChestItems", -1, true);
	}
	
	@Override
	public void loadDefaultValues() {
		this.addItem(XMaterial.WOODEN_AXE.parseItem());
		this.addItem(XMaterial.SUGAR_CANE.parseItem());
		this.addItem(XMaterial.APPLE.parseItem());
		this.addItem(XMaterial.PORKCHOP.parseItem());
		this.addItem(XMaterial.OAK_WOOD.parseItem());
		this.addItem(XMaterial.STICK.parseItem());
		this.addItem(XMaterial.COBBLESTONE.parseItem());
		this.addItem(XMaterial.SAND.parseItem());
	}
}