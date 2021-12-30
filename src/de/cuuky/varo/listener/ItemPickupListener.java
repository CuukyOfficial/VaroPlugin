package de.cuuky.varo.listener;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import de.cuuky.varo.Main;

public class ItemPickupListener implements Listener {

	@EventHandler
	public void onInventoryMove(PlayerPickupItemEvent event) {
		if(!Main.getVaroGame().hasStarted() && event.getPlayer().getGameMode() != GameMode.CREATIVE)
			event.setCancelled(true);
	}
}
