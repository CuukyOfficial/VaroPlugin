package de.cuuky.varo.listener;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;

public class PlayerCommandPreprocessListener implements Listener {
	
	private static final List<String> WORLDEDIT_CRASH_COMMANDS = Arrays.asList("//calc", "/worldedit:/calc", "//calculate", "/worldedit:/calculate", "//eval", "/worldedit:/eval", "//evaluate", "/worldedit:/evaluate", "//solve", "/worldedit:/solve");
	private static final Pattern CRASH_DETECT_PATTERN = Pattern.compile(".+for\\(.+for\\(.+for\\(.+", Pattern.CASE_INSENSITIVE);
	private static final Pattern CRASH_DETECT_PATTERN_SEVERE = Pattern.compile(".+\\sfor\\([a-z]+=0;[a-z]+<256;[a-z]+\\+\\+\\)\\{for\\([a-z]+=0;[a-z]+<256;[a-z]+\\+\\+\\)\\{for\\([a-z]+=0;[a-z]+<256;[a-z]+\\+\\+\\)\\{for\\([a-z]+=0;[a-z]+<256;[a-z]+\\+\\+\\)\\{\\}\\}\\}\\}", Pattern.CASE_INSENSITIVE);
	private static final List<String> TELL_COMMANDS = Arrays.asList("/tell", "/bukkit:tell", "/minecraft:tell", "/me", "/bukkit:me", "/minecraft:me");

	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
		String lowerMessage = event.getMessage().split(" ", 2)[0].toLowerCase();
		
		if (WORLDEDIT_CRASH_COMMANDS.contains(lowerMessage)) {
			event.setCancelled(true);
			if (CRASH_DETECT_PATTERN.matcher(event.getMessage()).matches()) {
				if (CRASH_DETECT_PATTERN_SEVERE.matcher(event.getMessage()).matches())
					Bukkit.getServer().broadcastMessage(String.format("%s§e%s §chat mit hoher Sicherheit versucht den Server zu crashen!", Main.getPrefix(), event.getPlayer().getName(), event.getMessage()));
				else
					Bukkit.getServer().broadcastMessage(String.format("%s§e%s §chat möglicherweise versucht den Server zu crashen!", Main.getPrefix(), event.getPlayer().getName(), event.getMessage()));
			} else
				event.getPlayer().sendMessage(Main.getPrefix() + ConfigMessages.COMMANDS_DENIED.getValue(VaroPlayer.getPlayer(event.getPlayer())));
		} else if (TELL_COMMANDS.contains(lowerMessage)) {
			event.setCancelled(true);
			event.getPlayer().sendMessage(Main.getPrefix() + ConfigMessages.COMMANDS_DENIED.getValue(VaroPlayer.getPlayer(event.getPlayer())));
		}
	}
}