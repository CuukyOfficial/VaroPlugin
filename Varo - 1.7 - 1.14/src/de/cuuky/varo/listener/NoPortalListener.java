package de.cuuky.varo.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import de.cuuky.varo.config.config.ConfigEntry;

public class NoPortalListener implements Listener {

	public boolean netherPortalNearby(Location location, int radius) {
		for(int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
			for(int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
				for(int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
					if(location.getWorld().getBlockAt(x, y, z).getType().name().contains("PORTAL"))
						return true;
				}
			}
		}
		return false;
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if(!ConfigEntry.BLOCK_USER_PORTALS.getValueAsBoolean())
			return;

		if(event.getPlayer().hasPermission("varo.portals"))
			return;

		if(!event.getBlock().getType().equals(Material.OBSIDIAN))
			return;

		if(!netherPortalNearby(event.getBlock().getLocation(), 1))
			return;

		event.setCancelled(true);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if(!ConfigEntry.BLOCK_USER_PORTALS.getValueAsBoolean())
			return;

		if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
			return;

		if(event.getPlayer().hasPermission("varo.portals"))
			return;

		if(event.getPlayer().getItemInHand() == null)
			return;

		if(!event.getPlayer().getItemInHand().getType().equals(Material.FLINT_AND_STEEL) && !event.getPlayer().getItemInHand().getType().equals(Material.WATER_BUCKET))
			return;

		if(event.getPlayer().getItemInHand().getType().equals(Material.WATER_BUCKET) && !netherPortalNearby(event.getClickedBlock().getLocation(), 1))
			return;

		if(!event.getClickedBlock().getType().equals(Material.OBSIDIAN))
			return;

		event.setCancelled(true);
	}
}
