package de.cuuky.varo.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import de.cuuky.varo.Main;

public class PlayerCommandPreprocessListener implements Listener {

	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
		String lowerMessage = event.getMessage().toLowerCase();
		if(lowerMessage.startsWith("/pl") || lowerMessage.startsWith("/plugins") || lowerMessage.startsWith("/?") || lowerMessage.startsWith("/bukkit:?") || lowerMessage.startsWith("/bukkit:pl") || lowerMessage.startsWith("/bukkit:plugins") || lowerMessage.startsWith("/help") || lowerMessage.startsWith("/bukkit:help") || lowerMessage.startsWith("/me") || lowerMessage.startsWith("/minecraft:me") || lowerMessage.startsWith("/tell") || lowerMessage.startsWith("/minecraft:tell") || lowerMessage.startsWith("/icanhasbukkit") || lowerMessage.startsWith("/version") || lowerMessage.startsWith("/bukkit:version") || lowerMessage.startsWith("/ver") || lowerMessage.startsWith("/about") || lowerMessage.startsWith("/bukkit:about")) {

			if(event.getPlayer().hasPermission("varo.readInfo"))
				return;

			if(Main.getGame().isRunning())
				if(lowerMessage.contains("tell"))
					return;

			event.setCancelled(true);
			event.getPlayer().sendMessage(Main.getPrefix() + "§7Nein.");
		}
	}
}
