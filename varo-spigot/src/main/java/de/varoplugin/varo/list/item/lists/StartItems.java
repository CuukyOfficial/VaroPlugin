package de.varoplugin.varo.list.item.lists;

import org.bukkit.inventory.ItemStack;

import com.cryptomorin.xseries.XMaterial;

import de.varoplugin.varo.list.item.ItemList;
import de.varoplugin.varo.player.VaroPlayer;

public class StartItems extends ItemList {

	public StartItems() {
		super("StartItems", 36, false);
	}
	
	@Override
	public void loadDefaultValues() {
		this.addItem(XMaterial.AIR.parseItem());
	}

	public void giveToAll() {
		for (VaroPlayer player : VaroPlayer.getOnlineAndAlivePlayer())
			for (ItemStack item : getItems())
				player.getPlayer().getInventory().addItem(item);
	}
}