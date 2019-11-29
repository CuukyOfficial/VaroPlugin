package de.cuuky.varo.gui.events;

import java.util.ArrayList;
import java.util.Collections;

import de.cuuky.varo.logger.LoggerMaster;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.Main;
import de.cuuky.varo.gui.SuperInventory;
import de.cuuky.varo.gui.utils.PageAction;
import de.cuuky.varo.item.ItemBuilder;
import de.cuuky.varo.version.types.Materials;

public class EventListGUI extends SuperInventory {

	public EventListGUI(Player opener) {
		super("§5Events", opener, 45, false);

		open();
	}

	@Override
	public boolean onOpen() {
		ArrayList<String> list = LoggerMaster.getInstance().getEventLogger().getLogs();
		Collections.reverse(list);

		int start = getSize() * (getPage() - 1);
		for(int i = 0; i != getSize(); i++) {
			String[] line;
			try {
				line = list.get(start).split("] ");
			} catch(IndexOutOfBoundsException e) {
				break;
			}

			line[0] = line[0].replace("[", "");
			ArrayList<String> s = new ArrayList<>();
			s.add("§c" + line[0]);
			linkItemTo(i, new ItemBuilder().displayname("§7" + line[1]).itemstack(new ItemStack(Materials.SIGN.parseMaterial())).lore(s).build(), new Runnable() {

				@Override
				public void run() {

				}
			});
			start++;
		}

		return calculatePages(list.size(), getSize()) == page;
	}

	@Override
	public void onClick(InventoryClickEvent event) {}

	@Override
	public void onInventoryAction(PageAction action) {}

	@Override
	public boolean onBackClick() {
		return false;
	}

	@Override
	public void onClose(InventoryCloseEvent event) {}
}
