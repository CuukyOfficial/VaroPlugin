package de.cuuky.varo.listener.saveable;

import java.util.Iterator;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Furnace;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import de.cuuky.varo.entity.player.stats.stat.inventory.VaroSaveable;
import de.cuuky.varo.game.Game;

public class EntityExplodeListener implements Listener {

	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {
		if(!Game.getInstance().hasStarted()) {
			event.setCancelled(true);
			return;
		}

		final Iterator<Block> iter = event.blockList().iterator();
		while(iter.hasNext()) {
			Block block = iter.next();
			if(block.getState() instanceof Chest || block.getState() instanceof Furnace) {
				if(VaroSaveable.getByLocation(block.getLocation()) != null)
					iter.remove();
			} else if(block.getState() instanceof Sign)
				if(chestNearby(block.getLocation()))
					iter.remove();
		}
	}

	private boolean chestNearby(Location location) {
		for(int x = location.getBlockX() - 1; x <= location.getBlockX() + 1; x++) {
			for(int y = location.getBlockY() - 1; y <= location.getBlockY() + 1; y++) {
				for(int z = location.getBlockZ() - 1; z <= location.getBlockZ() + 1; z++) {
					Location loc = new Location(location.getWorld(), x, y, z);
					if(VaroSaveable.getByLocation(loc) != null)
						return true;
				}
			}
		}
		return false;
	}
}