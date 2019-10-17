package de.cuuky.varo.listener.spectator;

import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.spigotmc.event.entity.EntityMountEvent;

import de.cuuky.varo.vanish.Vanish;

public class EntityMountListener implements Listener {

	@EventHandler
	public void onEntityMount(EntityMountEvent event) {
		if (cancelEvent(event.getEntity()))
			event.setCancelled(true);
	}

	private static boolean cancelEvent(Entity interact) {
		if (!(interact instanceof Player))
			return false;

		Player player = (Player) interact;

		if (Vanish.getVanish(player) == null || player.getGameMode() != GameMode.ADVENTURE)
			return false;

		return true;
	}
}
