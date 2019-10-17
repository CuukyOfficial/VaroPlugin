package de.cuuky.varo.listener.utils;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class InventoryClickUtil {

	private InventoryClickEvent event;

	public InventoryClickUtil(InventoryClickEvent event) {
		this.event = event;
	}

	public Inventory getInventory() {
		if(event.getWhoClicked().getOpenInventory() == null)
			return null;

		return event.getWhoClicked().getOpenInventory().getTopInventory();
	}

}
