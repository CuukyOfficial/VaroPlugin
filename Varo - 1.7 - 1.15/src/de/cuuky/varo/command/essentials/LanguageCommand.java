package de.cuuky.varo.command.essentials;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.cfw.configuration.language.Language;
import de.cuuky.varo.Main;
import de.cuuky.varo.entity.player.VaroPlayer;

public class LanguageCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(Main.getPrefix() + "Not for console!");
			return false;
		}
		
		if (args.length == 0) {
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/language §7<languagecode>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/language list");
			return false;
		}

		if (args[0].equalsIgnoreCase("list")) {
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "§lList of all messages:");
			for (String language : Main.getLanguageManager().getLanguages().keySet())
				sender.sendMessage(Main.getPrefix() + language);
			return false;
		}

		Language lang = Main.getLanguageManager().getLanguages().get(args[0]);
		if (lang == null) {
			sender.sendMessage(Main.getPrefix() + "Language " + args[0] + " is not useable on this server! §a/language list");
			return false;
		}
		
		VaroPlayer.getPlayer((Player) sender).setLocale(args[0]);
		sender.sendMessage(Main.getPrefix() + "Language successfully changed to " + args[0] + "!");
		return false;
	}
}