package de.cuuky.varo.listener.saveable;

import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.block.Furnace;
import org.bukkit.block.Hopper;
import org.bukkit.entity.minecart.HopperMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;

import de.cuuky.varo.app.Main;
import de.cuuky.varo.entity.player.stats.stat.inventory.VaroSaveable;

public class InventoryMoveSavableListener implements Listener {

	@EventHandler
	public void onInventoryMove(InventoryMoveItemEvent e) {
		if (!Main.getVaroGame().hasStarted())
			return;

		if (!(e.getInitiator().getHolder() instanceof Hopper || e.getInitiator().getHolder() instanceof HopperMinecart))
			return;

		if (!(e.getSource().getHolder() instanceof Chest) && !(e.getSource().getHolder() instanceof Furnace) && !(e.getSource().getHolder() instanceof DoubleChest))
			return;

		if (e.getSource().getHolder() instanceof Chest) {
			Chest chest = (Chest) e.getSource().getHolder();
			if (VaroSaveable.getByLocation(chest.getLocation()) != null) {
				e.setCancelled(true);
				return;
			}
		} else if (e.getSource().getHolder() instanceof DoubleChest) {
			DoubleChest dc = (DoubleChest) e.getSource().getHolder();
			Chest r = (Chest) dc.getRightSide();
			Chest l = (Chest) dc.getLeftSide();
			if (VaroSaveable.getByLocation(l.getLocation()) != null || VaroSaveable.getByLocation(r.getLocation()) != null) {
				e.setCancelled(true);
				return;
			}
		} else {
			Furnace f = (Furnace) e.getSource().getHolder();
			if (VaroSaveable.getByLocation(f.getLocation()) != null) {
				e.setCancelled(true);
				return;
			}
		}
	}
}