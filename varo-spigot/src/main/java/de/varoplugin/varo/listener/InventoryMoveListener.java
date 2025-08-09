package de.varoplugin.varo.listener;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

import de.varoplugin.varo.Main;

public class InventoryMoveListener implements Listener {

	@EventHandler
	public void onInventoryMove(InventoryClickEvent event) {
		if(!Main.getVaroGame().hasStarted() && event.getWhoClicked().getGameMode() != GameMode.CREATIVE)
			event.setCancelled(true);
	}
	
	@EventHandler
	public void onInventoryMove(InventoryDragEvent event) {
		if(!Main.getVaroGame().hasStarted() && event.getWhoClicked().getGameMode() != GameMode.CREATIVE)
			event.setCancelled(true);
	}
}
