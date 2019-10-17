package de.cuuky.varo.gui.utils;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import de.cuuky.varo.gui.SuperInventory;
import de.cuuky.varo.listener.utils.InventoryClickUtil;
import de.cuuky.varo.version.types.Sounds;

public class InventoryListener implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInvClick(InventoryClickEvent event) {
		Inventory inventory = new InventoryClickUtil(event).getInventory();
		if (inventory == null || event.getCurrentItem() == null || event.getCurrentItem().getItemMeta() == null
				|| event.getCurrentItem().getItemMeta().getDisplayName() == null)
			return;

		Player player = (Player) event.getWhoClicked();
		String itemName = event.getCurrentItem().getItemMeta().getDisplayName();

		for (int i = 0; i < SuperInventory.getGUIS().size(); i++) {
			SuperInventory inv = SuperInventory.getGUIS().get(i);
			if (!inv.getInventory().equals(inventory))
				continue;

			player.playSound(player.getLocation(), Sounds.CLICK.bukkitSound(), 1, 1);
			event.setCancelled(true);
			if (itemName.equals("§c"))
				return;

			switch (itemName) {
			case "§aSeite vorwärts":
				inv.pageForwards();
				inv.pageActionChanged(PageAction.PAGE_SWITCH_FORWARDS);
				return;
			case "§cSeite rückwärts":
				inv.pageBackwards();
				inv.pageActionChanged(PageAction.PAGE_SWITCH_FORWARDS);
				return;
			case "§4Schließen":
				inv.closeInventory();
				return;
			case "§4Zurück":
				inv.closeInventory();
				inv.back();
				return;
			default:
				break;
			}

			inv.executeLink(event.getCurrentItem());
			inv.onClick(event);
			break;
		}
	}

	@EventHandler
	public void onInvClose(InventoryCloseEvent event) {
		if (event.getInventory() == null)
			return;

		SuperInventory inv1 = null;
		for (SuperInventory inv : SuperInventory.getGUIS()) {
			if (!inv.getInventory().equals(event.getInventory()))
				continue;

			inv1 = inv;
			break;
		}

		if (inv1 != null) {
			inv1.onClose(event);
			inv1.closeInventory();
		}
	}
}
