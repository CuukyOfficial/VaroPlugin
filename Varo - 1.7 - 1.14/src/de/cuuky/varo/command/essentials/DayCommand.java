package de.cuuky.varo.command.essentials;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;

public class DayCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("varo.day")) {
			sender.sendMessage(VaroCommand.getNoPermission("varo.day"));
			return false;
		}

		World world = sender instanceof Player ? ((Player) sender).getWorld() : Main.getDataManager().getWorldHandler().getWorld();
		world.setTime(1000);
		sender.sendMessage(Main.getPrefix() + "Es ist jetzt " + Main.getColorCode() + "Tag§7!");
		return false;
	}

}
