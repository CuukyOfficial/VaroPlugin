package de.cuuky.varo.list.item.lists;

import de.cuuky.cfw.version.VersionUtils;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.list.item.ItemList;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class StartItems extends ItemList {

	public StartItems() {
		super("StartItems", 36, false);
	}
	
	@Override
	public void loadDefaultValues() {
		this.addItem(Materials.AIR.parseItem());
	}

	public void giveToAll() {
		for (Player player : VersionUtils.getVersionAdapter().getOnlinePlayers())
			for (ItemStack item : getItems())
				player.getInventory().addItem(item);
	}
}