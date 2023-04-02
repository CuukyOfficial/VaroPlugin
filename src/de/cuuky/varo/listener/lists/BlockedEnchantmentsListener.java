package de.cuuky.varo.listener.lists;

import java.util.Map.Entry;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.list.enchantment.lists.BlockedEnchantments;

public class BlockedEnchantmentsListener implements Listener {

	@EventHandler
	public void onEnchant(EnchantItemEvent event) {
		if (event.getItem() == null)
			return;

		BlockedEnchantments blockedEnchantments = Main.getDataManager().getListManager().getBlockedEnchantments();
		for (Entry<Enchantment, Integer> enchantment : event.getEnchantsToAdd().entrySet())
			if (blockedEnchantments.isBlocked(enchantment.getKey(), enchantment.getValue()))
				event.getEnchantsToAdd().remove(enchantment.getKey());
		
		if (event.getEnchantsToAdd().isEmpty()) {
		    event.setCancelled(true);
            VaroPlayer vp = VaroPlayer.getPlayer(event.getEnchanter());
            event.getEnchanter().sendMessage(Main.getPrefix() + ConfigMessages.NOPERMISSION_NOT_ALLOWED_CRAFT.getValue(vp, vp));
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.isCancelled())
			return;

		Inventory inv = event.getInventory();

		if (!(inv instanceof AnvilInventory))
			return;

		InventoryView view = event.getView();
		int rawSlot = event.getRawSlot();

		if (rawSlot != view.convertSlot(rawSlot) || rawSlot != 2)
			return;

		ItemStack item = event.getCurrentItem();
		if (item == null)
			return;

		for (Enchantment enc : item.getEnchantments().keySet())
			if (Main.getDataManager().getListManager().getBlockedEnchantments().isBlocked(enc, item.getEnchantments().get(enc))) {
				event.setCancelled(true);
				VaroPlayer vp = VaroPlayer.getPlayer((Player) event.getWhoClicked());
				vp.sendMessage(ConfigMessages.NOPERMISSION_NOT_ALLOWED_CRAFT);
				return;
			}
	}
}