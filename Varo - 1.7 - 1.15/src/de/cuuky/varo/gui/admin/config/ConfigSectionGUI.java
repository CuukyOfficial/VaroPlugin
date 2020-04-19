package de.cuuky.varo.gui.admin.config;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.cfw.item.ItemBuilder;
import de.cuuky.cfw.menu.SuperInventory;
import de.cuuky.cfw.menu.utils.PageAction;
import de.cuuky.cfw.utils.JavaUtils;
import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSettingSection;
import de.cuuky.varo.gui.admin.AdminMainMenu;

public class ConfigSectionGUI extends SuperInventory {

	public ConfigSectionGUI(Player opener) {
		super("§aConfig-Section", opener, JavaUtils.getNextToNine(ConfigSettingSection.values().length), false);

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
		int i = -1;
		for (ConfigSettingSection section : ConfigSettingSection.values()) {
			i++;

			linkItemTo(i, new ItemBuilder().displayname("§7" + section.getName()).itemstack(new ItemStack(section.getMaterial())).lore((JavaUtils.getArgsToString(JavaUtils.addIntoEvery(section.getDescription().split("\n"), Main.getColorCode(), true), "\n")).split("\n")).build(), new Runnable() {

				@Override
				public void run() {
					new ConfigGUI(getOpener(), section);
				}
			});
		}

		return true;
	}
}
