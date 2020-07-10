package de.cuuky.varo.gui.admin.alert;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.cfw.item.ItemBuilder;
import de.cuuky.cfw.menu.SuperInventory;
import de.cuuky.cfw.menu.utils.PageAction;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.Main;
import de.cuuky.varo.alert.Alert;
import de.cuuky.varo.gui.admin.alert.AlertChooseGUI.AlertGUIType;

public class AlertOptionGUI extends SuperInventory {

	private Alert alert;
	private AlertGUIType type;

	public AlertOptionGUI(Player opener, Alert alert, AlertGUIType type) {
		super("§7Alert §c" + alert.getId(), opener, 18, false);

		this.type = type;
		this.alert = alert;
		this.setModifier = true;
		Main.getCuukyFrameWork().getInventoryManager().registerInventory(this);
		open();
	}

	@Override
	public boolean onBackClick() {
		new AlertChooseGUI(opener, type);
		return true;
	}

	@Override
	public void onClick(InventoryClickEvent event) {
		updateInventory();
	}

	@Override
	public void onClose(InventoryCloseEvent event) {}

	@Override
	public void onInventoryAction(PageAction action) {}

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
}
