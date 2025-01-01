package de.cuuky.varo.command.essentials;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.language.Messages;
import io.github.almightysatan.slams.Placeholder;
import net.md_5.bungee.api.ChatColor;

public class BroadcastCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("varo.broadcast")) {
			Messages.COMMANDS_ERROR_PERMISSION.send(sender);
			return false;
		}

		if (args.length == 0) {
			sender.sendMessage(Main.getPrefix() + "§7/bc <Message>");
			return false;
		}

		String msg = "";
		for (String arg : args)
			if (!msg.equals(""))
				msg = msg + " " + arg;
			else
				msg = arg;

		Messages.COMMANDS_BROADCAST_FORMAT.send(sender, Placeholder.constant("message", ChatColor.translateAlternateColorCodes('&', msg)));
		return false;
	}

}