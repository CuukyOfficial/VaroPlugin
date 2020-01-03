package de.cuuky.varo.gui.admin.config;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.config.ConfigSection;
import de.cuuky.varo.gui.SuperInventory;
import de.cuuky.varo.gui.admin.AdminMainMenu;
import de.cuuky.varo.gui.utils.PageAction;
import de.cuuky.varo.item.ItemBuilder;

public class ConfigSectionGUI extends SuperInventory {

	public ConfigSectionGUI(Player opener) {
		super("§aConfig-Section", opener, 27, false);

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
		int i = -1;
		for(ConfigSection section : ConfigSection.values()) {
			i++;

			linkItemTo(i, new ItemBuilder().displayname("§7" + section.getName()).itemstack(new ItemStack(section.getMaterial())).lore((Main.getColorCode() + section.getDescription()).split("\n")).build(), new Runnable() {

				@Override
				public void run() {
					new ConfigGUI(getOpener(), section);
				}
			});
		}

		return true;
	}
}
