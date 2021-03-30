package de.cuuky.varo.list.item.lists;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.list.item.ItemList;

public class StartItems extends ItemList {

	public StartItems() {
		super("StartItems");

		if (!items.isEmpty())
			return;

		items.add(Materials.AIR.parseItem());
	}

	public void giveToAll() {
		for (Player player : Bukkit.getOnlinePlayers())
			for (ItemStack item : items)
				player.getInventory().addItem(item);
	}
}