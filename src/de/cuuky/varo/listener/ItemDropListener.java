package de.cuuky.varo.listener;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import de.cuuky.varo.app.Main;

public class ItemDropListener implements Listener {

	@EventHandler
	public void onInventoryMove(PlayerDropItemEvent event) {
		if(!Main.getVaroGame().hasStarted() && event.getPlayer().getGameMode() != GameMode.CREATIVE)
			event.setCancelled(true);
	}
}
