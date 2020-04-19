package de.cuuky.varo.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class VaroEventListener implements Listener {

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		for (VaroEvent event1 : VaroEvent.getEnabledEvents())
			event1.onMove(event);
	}
}
