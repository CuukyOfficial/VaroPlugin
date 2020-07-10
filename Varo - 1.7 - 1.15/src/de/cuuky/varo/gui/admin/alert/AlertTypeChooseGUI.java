package de.cuuky.varo.gui.admin.alert;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.cfw.item.ItemBuilder;
import de.cuuky.cfw.menu.SuperInventory;
import de.cuuky.cfw.menu.utils.PageAction;
import de.cuuky.varo.Main;
import de.cuuky.varo.gui.admin.AdminMainMenu;
import de.cuuky.varo.gui.admin.alert.AlertChooseGUI.AlertGUIType;

public class AlertTypeChooseGUI extends SuperInventory {

	public AlertTypeChooseGUI(Player opener) {
		super("§eChoose Alert", opener, 18, false);

		this.setModifier = true;
		Main.getCuukyFrameWork().getInventoryManager().registerInventory(this);
		open();
	}

	@Override
	public boolean onBackClick() {
		new AdminMainMenu(opener);
		return true;
	}

	@Override
	public void onClick(InventoryClickEvent event) {}

	@Override
	public void onClose(InventoryCloseEvent event) {}

	@Override
	public void onInventoryAction(PageAction action) {}

	@Override
	public boolean onOpen() {
		int i = 2;
		for (AlertGUIType type : AlertGUIType.values()) {
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
}
