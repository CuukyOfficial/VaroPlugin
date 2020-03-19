package de.cuuky.varo.item.hook;

import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public interface ItemHookListener {
	
	public void onEntityHit(EntityDamageByEntityEvent event);

	public void onInteract(PlayerInteractEvent event);

	public void onInteractEntity(PlayerInteractEntityEvent event);

}