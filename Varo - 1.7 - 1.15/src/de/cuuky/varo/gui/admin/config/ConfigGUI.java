package de.cuuky.varo.gui.admin.config;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.config.ConfigEntry;
import de.cuuky.varo.configuration.config.ConfigSection;
import de.cuuky.varo.gui.SuperInventory;
import de.cuuky.varo.gui.utils.PageAction;
import de.cuuky.varo.gui.utils.chat.ChatHook;
import de.cuuky.varo.gui.utils.chat.ChatHookListener;
import de.cuuky.varo.item.ItemBuilder;
import de.cuuky.varo.utils.JavaUtils;
import de.cuuky.varo.version.types.Materials;
import de.cuuky.varo.version.types.Sounds;

public class ConfigGUI extends SuperInventory {

	private ConfigSection section;

	public ConfigGUI(Player opener, ConfigSection section) {
		super("ßa" + section.getName(), opener, 18, false);

		this.section = section;

		open();
	}

	private void hookChat(ConfigEntry entry) {
		new ChatHook(opener, "ß7Gib einen Wert ein f√ºr " + Main.getColorCode() + entry.getName() + " ß8(ß7Aktuell: ßa" + entry.getValue() + "ß8):", new ChatHookListener() {

			@Override
			public void onChat(String message) {
				if(message.equalsIgnoreCase("cancel")) {
					opener.sendMessage(Main.getPrefix() + "ß7Aktion erfolgreich abgebrochen!");
				} else {
					try {
						entry.setValue(JavaUtils.getStringObject(message), true);

					} catch(Exception e) {
						opener.sendMessage(Main.getPrefix() + e.getMessage());
						hookChat(entry);
						return;
					}

					opener.playSound(opener.getLocation(), Sounds.ANVIL_LAND.bukkitSound(), 1, 1);
					opener.sendMessage(Main.getPrefix() + "ß7'ßa" + entry.getName() + "ß7' erfolgreich auf 'ßa" + message + "ß7' gesetzt!");
				}

				reopenSoon();
			}
		});
		opener.sendMessage(Main.getPrefix() + "ß7Gib zum Abbruch ßccancelß7 ein.");
	}

	@Override
	public boolean onBackClick() {
		new ConfigSectionGUI(getOpener());
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
		for(ConfigEntry entry : section.getEntries()) {
			i++;
			ArrayList<String> lore = new ArrayList<>();
			for(String strin : entry.getDescription())
				lore.add(Main.getColorCode() + strin);

			lore.add(" ");
			lore.add("Value: " + entry.getValue());

			linkItemTo(i, new ItemBuilder().displayname("ß7" + entry.getPath()).itemstack(new ItemStack(Materials.SIGN.parseMaterial())).lore(lore).build(), new Runnable() {

				@Override
				public void run() {
					close(false);
					hookChat(entry);
				}
			});
		}

		return true;
	}
}