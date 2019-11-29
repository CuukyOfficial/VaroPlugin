package de.cuuky.varo.gui.admin.orelogger;

import java.util.ArrayList;
import java.util.Collections;

import de.cuuky.varo.logger.LoggerMaster;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.Main;
import de.cuuky.varo.gui.SuperInventory;
import de.cuuky.varo.gui.admin.AdminMainMenu;
import de.cuuky.varo.gui.utils.PageAction;
import de.cuuky.varo.item.ItemBuilder;

public class OreLoggerListGUI extends SuperInventory {

	public OreLoggerListGUI(Player opener) {
		super("§6OreLogger", opener, 45, false);

		open();
	}

	@Override
	public boolean onOpen() {
		ArrayList<String> list = LoggerMaster.getInstance().getBlockLogger().getLogs();
		Collections.reverse(list);

		int start = getSize() * (getPage() - 1);
		for(int i = 0; i != getSize(); i++) {
			String str;
			try {
				str = list.get(start);
			} catch(IndexOutOfBoundsException e) {
				break;
			}

			String name = str.split("\\] ")[1].split(" ")[0];
			ArrayList<String> lore = new ArrayList<>();

			String minedAt = str.split("at ")[1].replaceAll("!", "");

			Material blocktype = Material.matchMaterial(str.split("mined ")[1].split(" ")[0]);
			Location loc = new Location(Bukkit.getWorld(minedAt.split("\\'")[1]), Integer.valueOf(minedAt.split("x:")[1].split(" ")[0]), Integer.valueOf(minedAt.split("y:")[1].split(" ")[0]), Integer.valueOf(minedAt.split("z:")[1].split(" ")[0]));

			lore.add("Block Type: §c" + blocktype.name());
			lore.add("Mined at: §c" + minedAt);
			lore.add("Time mined: §c" + str.split("\\]")[0].split("\\[")[1]);
			lore.add("Mined by: " + Main.getColorCode() + name);
			lore.add(" ");
			lore.add("§cClick to teleport!");

			linkItemTo(i, new ItemBuilder().displayname(name).itemstack(new ItemStack(blocktype)).lore(lore).build(), new Runnable() {

				@Override
				public void run() {
					opener.teleport(loc);
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
		new AdminMainMenu(opener);
		return true;
	}

	@Override
	public void onClose(InventoryCloseEvent event) {}
}
