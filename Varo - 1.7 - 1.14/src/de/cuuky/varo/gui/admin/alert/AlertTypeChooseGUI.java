package de.cuuky.varo.gui.admin.alert;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.gui.SuperInventory;
import de.cuuky.varo.gui.admin.AdminMainMenu;
import de.cuuky.varo.gui.admin.alert.AlertChooseGUI.AlertGUIType;
import de.cuuky.varo.gui.utils.PageAction;
import de.cuuky.varo.item.ItemBuilder;

public class AlertTypeChooseGUI extends SuperInventory {

	public AlertTypeChooseGUI(Player opener) {
		super("§eChoose Alert", opener, 9, false);

		open();
	}

	@Override
	public boolean onOpen() {
		int i = 2;
		for(AlertGUIType type : AlertGUIType.values()) {
			linkItemTo(i, new ItemBuilder().displayname(type.getTypeName()).itemstack(new ItemStack(type.getIcon())).amount(getFixedSize(type.getList().size())).build(), new Runnable() {

				@Override
				public void run() {
					new AlertChooseGUI(opener, type);
				}
			});
			i += 2;
		}
		return true;
	}

	@Override
	public void onClick(InventoryClickEvent event) {}

	@Override
	public void onInventoryAction(PageAction action) {}

	@Override
	public boolean onBackClick() {
		new AdminMainMenu(opener);
		return true;
	}

	@Override
	public void onClose(InventoryCloseEvent event) {}
}
