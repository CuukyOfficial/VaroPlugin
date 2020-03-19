package de.cuuky.varo.item.hook;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class HookListener implements Listener {

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if(event.getItem() == null)
			return;

		ItemHook hook = ItemHook.getItemHook(event.getItem(), event.getPlayer());
		if(hook == null)
			return;

		hook.getHookListener().onInteract(event);
	}

	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		if(event.getPlayer().getItemInHand() == null || event.getRightClicked() == null)
			return;

		ItemHook hook = ItemHook.getItemHook(event.getPlayer().getItemInHand(), event.getPlayer());
		if(hook == null)
			return;

		hook.getHookListener().onInteractEntity(event);
	}

	@EventHandler
	public void onPlayerDamageEntity(EntityDamageByEntityEvent event) {
		if(!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player) || ((Player) event.getDamager()).getItemInHand() == null)
			return;

		Player damager = (Player) event.getDamager();
		ItemHook hook = ItemHook.getItemHook(damager.getItemInHand(), damager);
		if(hook == null)
			return;

		hook.getHookListener().onEntityHit(event);
	}

	@EventHandler
	public void onItemMove(InventoryClickEvent event) {
		ItemHook hook = ItemHook.getItemHook(event.getCurrentItem(), (Player) event.getWhoClicked());
		if(hook != null && !hook.isDragable())
			event.setCancelled(true);
	}

	@EventHandler
	public void onItemDrop(PlayerDropItemEvent event) {
		ItemHook hook = ItemHook.getItemHook(event.getItemDrop().getItemStack(), event.getPlayer());
		if(hook != null && !hook.isDropable())
			event.setCancelled(true);
	}

	@EventHandler
	public void onItemPick(PlayerPickupItemEvent event) {
		ItemHook hook = ItemHook.getItemHook(event.getItem().getItemStack(), event.getPlayer());
		if(hook != null && !hook.isDropable())
			event.setCancelled(true);
	}
}