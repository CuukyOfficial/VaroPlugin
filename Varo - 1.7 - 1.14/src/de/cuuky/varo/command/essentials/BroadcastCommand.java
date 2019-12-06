package de.cuuky.varo.command.essentials;

import de.cuuky.varo.config.messages.ConfigMessages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;

public class BroadcastCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("varo.broadcast")) {
			sender.sendMessage(ConfigMessages.OTHER_NO_PERMISSION.getValue());
			return false;
		}

		if(args.length == 0) {
			sender.sendMessage(Main.getPrefix() + "§7/bc <Message>");
			return false;
		}

		String msg = "";
		for(String arg : args)
			if(!msg.equals(""))
				msg = msg + " " + arg;
			else
				msg = arg;

		Bukkit.broadcastMessage("§7[§cBroadcast§7] §c" + msg.replaceAll("&", "§"));
		return false;
	}

}