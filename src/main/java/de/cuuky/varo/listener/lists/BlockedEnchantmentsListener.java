package de.cuuky.varo.listener.lists;

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
import de.cuuky.varo.player.VaroPlayer;

public class BlockedEnchantmentsListener implements Listener {

	@EventHandler
	public void onEnchant(EnchantItemEvent event) {
		if (event.getItem() == null) return;

		Main.getInstance().getServer().getScheduler().runTask(Main.getInstance(), () -> {
			ItemStack enchantedItem = event.getItem();
			boolean removed = false;

			for (Enchantment enc : enchantedItem.getEnchantments().keySet()) {
				if (Main.getDataManager().getListManager().getBlockedEnchantments().isBlocked(enc, enchantedItem.getEnchantments().get(enc))) {
					enchantedItem.removeEnchantment(enc);
					removed = true;
				}
			}

			if (removed) {
				VaroPlayer vp = VaroPlayer.getPlayer(event.getEnchanter());
				event.getEnchanter().sendMessage(Main.getPrefix() + ConfigMessages.NOPERMISSION_NOT_ALLOWED_CRAFT.getValue(vp, vp));
			}
		});
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.isCancelled()) return;

		Inventory inv = event.getInventory();

		if (!(inv instanceof AnvilInventory)) return;

		InventoryView view = event.getView();
		int rawSlot = event.getRawSlot();

		if (rawSlot != view.convertSlot(rawSlot) || rawSlot != 2) return;

		ItemStack item = event.getCurrentItem();
		if (item == null) return;

		Main.getInstance().getServer().getScheduler().runTask(Main.getInstance(), () -> {
			boolean removed = false;
			for (Enchantment enc : item.getEnchantments().keySet()) {
				if (Main.getDataManager().getListManager().getBlockedEnchantments().isBlocked(enc, item.getEnchantments().get(enc))) {
					item.removeEnchantment(enc);
					removed = true;
				}
			}
	
			if (removed) {
				VaroPlayer vp = VaroPlayer.getPlayer((Player) event.getWhoClicked());
				vp.sendMessage(ConfigMessages.NOPERMISSION_NOT_ALLOWED_CRAFT);
			}
		});
	}
}
