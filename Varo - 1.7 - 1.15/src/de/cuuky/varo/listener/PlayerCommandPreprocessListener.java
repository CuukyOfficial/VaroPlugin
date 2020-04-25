package de.cuuky.varo.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import de.cuuky.varo.Main;

public class PlayerCommandPreprocessListener implements Listener {

	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
		String lowerMessage = event.getMessage().toLowerCase();

		if (!lowerMessage.startsWith("/tell") && !lowerMessage.startsWith("/bukkit:tell"))
			return;
		if (Main.getVaroGame().isRunning())
			return;

		event.setCancelled(true);
		event.getPlayer().sendMessage(Main.getPrefix() + "§7Nein.");
	}
}