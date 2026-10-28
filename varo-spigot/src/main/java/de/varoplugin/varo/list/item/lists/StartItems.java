package de.varoplugin.varo.list.item.lists;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.cryptomorin.xseries.XMaterial;

import de.varoplugin.varo.Main;
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
		for (VaroPlayer varoPlayer : VaroPlayer.getOnlineAndAlivePlayer()) {
			final Player player = varoPlayer.getPlayer();
			if (player == null)
				continue;

			Main.scheduler().executeFor(player, () -> {
				for (ItemStack item : getItems())
					player.getInventory().addItem(item);
			});
		}
	}
}