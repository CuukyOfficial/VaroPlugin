package de.cuuky.varo.listener;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import de.cuuky.varo.Main;

public class PlayerCommandPreprocessListener implements Listener {
	
	private static final List<String> WORLDEDIT_CRASH_COMMANDS = Arrays.asList(new String[] {"//calc", "/worldedit:/calc", "//calculate", "/worldedit:/calculate", "//eval", "/worldedit:/eval", "//evaluate", "/worldedit:/evaluate", "//solve", "/worldedit:/solve"});
	private static final List<String> TELL_COMMANDS = Arrays.asList(new String[] {"/tell", "/bukkit:tell", "/me", "/bukkit:me"});

	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
		String lowerMessage = event.getMessage().split(" ", 2)[0].toLowerCase();
		
		if (WORLDEDIT_CRASH_COMMANDS.contains(lowerMessage)) {
			event.setCancelled(true);
			Bukkit.getServer().broadcastMessage(String.format("%s§e%s §chat möglicherweise versucht den Server zu crashen!", Main.getPrefix(), event.getPlayer().getName(), event.getMessage()));
		} else if (TELL_COMMANDS.contains(lowerMessage) && !Main.getVaroGame().isRunning()) {
			event.setCancelled(true);
			event.getPlayer().sendMessage(Main.getPrefix() + "§7Nein.");
		}
	}
}