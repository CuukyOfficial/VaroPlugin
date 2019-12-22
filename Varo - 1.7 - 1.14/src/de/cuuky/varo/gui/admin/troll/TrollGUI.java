package de.cuuky.varo.gui.admin.troll;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.gui.SuperInventory;
import de.cuuky.varo.gui.utils.PageAction;
import de.cuuky.varo.item.ItemBuilder;
import de.cuuky.varo.troll.TrollModule;

public class TrollGUI extends SuperInventory {

	public TrollGUI(Player opener) {
		super("§5Troll", opener, 9, false);

		open();
	}

	@Override
	public boolean onOpen() {
		int i = 1;
		for (TrollModule mod : TrollModule.getModules()) {
			linkItemTo(i, new ItemBuilder().displayname(mod.getName()).itemstack(new ItemStack(mod.getIcon())).lore(new String[]{"§7Enabled for: {2DO}", "", "§7" + mod.getDescription()}).build(), new Runnable() {

				@Override
				public void run() {

				}
			});

			i += 2;
		}
		return false;
	}

	@Override
	public void onClick(InventoryClickEvent event) {
	}

	@Override
	public void onInventoryAction(PageAction action) {
	}

	@Override
	public boolean onBackClick() {
		return false;
	}

	@Override
	public void onClose(InventoryCloseEvent event) {
	}
}
