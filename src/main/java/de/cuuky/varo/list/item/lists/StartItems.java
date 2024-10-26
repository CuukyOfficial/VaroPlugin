package de.cuuky.varo.list.item.lists;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.cryptomorin.xseries.XMaterial;

import de.cuuky.varo.list.item.ItemList;
import de.varoplugin.cfw.version.VersionUtils;

public class StartItems extends ItemList {

	public StartItems() {
		super("StartItems", 36, false);
	}
	
	@Override
	public void loadDefaultValues() {
		this.addItem(XMaterial.AIR.parseItem());
	}

	public void giveToAll() {
		for (Player player : VersionUtils.getVersionAdapter().getOnlinePlayers())
			for (ItemStack item : getItems())
				player.getInventory().addItem(item);
	}
}