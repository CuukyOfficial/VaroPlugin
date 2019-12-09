package de.cuuky.varo.command.essentials;

import de.cuuky.varo.config.messages.ConfigMessages;
import de.cuuky.varo.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;

public class NightCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("varo.night")) {
			sender.sendMessage(ConfigMessages.OTHER_NO_PERMISSION.getValue());
			return false;
		}

		if(sender instanceof Player)
			((Player) sender).getWorld().setTime(13000);
		else
			Utils.getMainWorld().setTime(13000);

		sender.sendMessage(Main.getPrefix() + "Es ist jetzt " + Main.getColorCode() + "Nacht§7!");
		return false;
	}
}