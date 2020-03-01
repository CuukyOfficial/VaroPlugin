package de.cuuky.varo.command.essentials;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.messages.ConfigMessages;
import de.cuuky.varo.threads.LagCounter;

public class UsageCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(!sender.hasPermission("varo.usage")) {
			sender.sendMessage(ConfigMessages.OTHER_NO_PERMISSION.getValue());
			return false;
		}

		Runtime r = Runtime.getRuntime();
		sender.sendMessage("§7-----------§7 §c§lSERVER-USAGE §7-----------");
		sender.sendMessage(Main.getPrefix() + "§7System OS: §c" + System.getProperty("os.name"));
		sender.sendMessage(Main.getPrefix() + "§7System Version: §c" + System.getProperty("os.version"));
		sender.sendMessage(Main.getPrefix() + "§7Java Version: §c" + System.getProperty("java.version"));
		sender.sendMessage(Main.getPrefix() + "§7Bukkit/Spigot Version: §c" + Bukkit.getVersion());
		sender.sendMessage(Main.getPrefix() + "§7Plugin Version: §c" + Main.getInstance().getDescription().getVersion());
		sender.sendMessage(Main.getPrefix() + "§7Total memory usage: §c" + (r.totalMemory() - r.freeMemory()) / 1048576 + "MB§7!");
		sender.sendMessage(Main.getPrefix() + "§7Total memory available: §c" + r.maxMemory() / 1048576 + "MB§7!");
		sender.sendMessage(Main.getPrefix() + "§7TPS: §c" + (double) Math.round(LagCounter.getTPS() * 100) / 100);
		return false;
	}

}