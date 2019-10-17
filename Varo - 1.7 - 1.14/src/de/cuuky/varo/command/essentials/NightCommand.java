package de.cuuky.varo.command.essentials;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;

public class NightCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("varo.night")) {
			sender.sendMessage(VaroCommand.getNoPermission("varo.night"));
			return false;
		}

		if(sender instanceof Player)
			((Player) sender).getWorld().setTime(13000);
		else
			Main.getDataManager().getWorldHandler().getWorld().setTime(13000);

		sender.sendMessage(Main.getPrefix() + "Es ist jetzt " + Main.getColorCode() + "Nacht§7!");
		return false;
	}

}
