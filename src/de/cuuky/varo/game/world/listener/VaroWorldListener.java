package de.cuuky.varo.game.world.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

import de.cuuky.varo.Main;

public class VaroWorldListener implements Listener {

	@EventHandler
	public void onWorldLoad(WorldLoadEvent event) {
		Main.getVaroGame().getVaroWorldHandler().addWorld(event.getWorld());
	}
}