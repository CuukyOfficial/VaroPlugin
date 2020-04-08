package de.cuuky.varo.listener.saveable;

import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.block.Furnace;
import org.bukkit.block.Hopper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;

import de.cuuky.varo.Main;
import de.cuuky.varo.entity.player.stats.stat.inventory.VaroSaveable;

public class InventoryMoveListener implements Listener {

	@EventHandler
	public void onInventoryMove(InventoryMoveItemEvent event) {
		if(!Main.getVaroGame().hasStarted())
			return;

		if(!(event.getInitiator().getHolder() instanceof Hopper))
			return;

		if(!(event.getSource().getHolder() instanceof Chest) && !(event.getSource().getHolder() instanceof Furnace) && !(event.getSource().getHolder() instanceof DoubleChest))
			return;

		if(event.getSource().getHolder() instanceof Chest) {
			Chest chest = (Chest) event.getSource().getHolder();
			if(VaroSaveable.getByLocation(chest.getLocation()) != null) {
				event.setCancelled(true);
				return;
			}
		} else if(event.getSource().getHolder() instanceof DoubleChest) {
			DoubleChest dc = (DoubleChest) event.getSource().getHolder();
			Chest r = (Chest) dc.getRightSide();
			Chest l = (Chest) dc.getLeftSide();
			if(VaroSaveable.getByLocation(l.getLocation()) != null || VaroSaveable.getByLocation(r.getLocation()) != null) {
				event.setCancelled(true);
				return;
			}
		} else {
			Furnace f = (Furnace) event.getSource().getHolder();
			if(VaroSaveable.getByLocation(f.getLocation()) != null) {
				event.setCancelled(true);
				return;
			}
		}
	}
}