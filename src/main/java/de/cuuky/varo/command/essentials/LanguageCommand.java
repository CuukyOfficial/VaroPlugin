package de.cuuky.varo.command.essentials;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.language.Messages;
import de.cuuky.varo.player.VaroPlayer;

public class LanguageCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	    if (!(sender instanceof Player)) {
	        Messages.COMMANDS_ERROR_NO_CONSOLE.send(sender);
			return false;
		}

		if (args.length == 0) {
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + label + " §7<languagecode>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + label + " list");
			return false;
		}

		if (args[0].equalsIgnoreCase("list")) {
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "§lList of all messages:");
			for (String language : Messages.LANGUAGES)
				sender.sendMessage(Main.getPrefix() + language);
			return false;
		}

		if (!Messages.LANGUAGES.contains(args[0])) {
			sender.sendMessage(Main.getPrefix() + "Language " + args[0] + " is not available on this server! §a/" + label + " list");
			return false;
		}

		VaroPlayer.getPlayer((Player) sender).setLanguage(args[0]);
		sender.sendMessage(Main.getPrefix() + "Language successfully changed to " + args[0] + "!");
		return false;
	}
}