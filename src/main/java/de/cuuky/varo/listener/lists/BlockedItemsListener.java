package de.cuuky.varo.listener.lists;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.language.Messages;
import de.cuuky.varo.player.VaroPlayer;

public class BlockedItemsListener implements Listener {

	@EventHandler
	public void onCraft(CraftItemEvent event) {
		if (event.getCurrentItem() == null)
			return;

		if (!Main.getDataManager().getListManager().getBlockedItems().isBlocked(event.getCurrentItem()) && !Main.getDataManager().getListManager().getBlockedRecipes().isBlocked(event.getCurrentItem()))
			return;

		event.setCancelled(true);
		VaroPlayer vp = VaroPlayer.getPlayer((Player) event.getWhoClicked());
		Messages.CRAFTING_DISALLOWED.send(vp);
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if (event.getItem() == null)
			return;

		if (!Main.getDataManager().getListManager().getBlockedItems().isBlocked(event.getItem()))
			return;

		event.setCancelled(true);
		VaroPlayer vp = VaroPlayer.getPlayer(event.getPlayer());
		Messages.CRAFTING_DISALLOWED.send(vp);
	}
}