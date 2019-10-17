package de.cuuky.varo.gui.admin.alert;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.alert.Alert;
import de.cuuky.varo.gui.SuperInventory;
import de.cuuky.varo.gui.admin.alert.AlertChooseGUI.AlertGUIType;
import de.cuuky.varo.gui.utils.PageAction;
import de.cuuky.varo.item.ItemBuilder;
import de.cuuky.varo.version.types.Materials;

public class AlertOptionGUI extends SuperInventory {

	private AlertGUIType type;
	private Alert alert;

	public AlertOptionGUI(Player opener, Alert alert, AlertGUIType type) {
		super("§7Alert §c" + alert.getId(), opener, 9, false);

		this.type = type;
		this.alert = alert;
		open();
	}

	@Override
	public boolean onOpen() {
		linkItemTo(4, new ItemBuilder().displayname(alert.isOpen() ? "§cClose" : "§aOpen").itemstack(new ItemStack(alert.isOpen() ? Materials.REDSTONE.parseMaterial() : Material.EMERALD)).build(), new Runnable() {

			@Override
			public void run() {
				alert.switchOpenState();
			}
		});

		return true;
	}

	@Override
	public void onClick(InventoryClickEvent event) {
		updateInventory();
	}

	@Override
	public void onInventoryAction(PageAction action) {}

	@Override
	public boolean onBackClick() {
		new AlertChooseGUI(opener, type);
		return true;
	}

	@Override
	public void onClose(InventoryCloseEvent event) {}
}
