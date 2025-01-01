package de.cuuky.varo.command.essentials;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.language.Messages;

public class DayCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("varo.day")) {
			Messages.COMMANDS_ERROR_PERMISSION.send(sender);
			return false;
		}

		World world = sender instanceof Player ? ((Player) sender).getWorld() : Main.getVaroGame().getVaroWorldHandler().getMainWorld().getWorld();
		world.setTime(1000);
		Messages.COMMANDS_TIME_DAY.send(sender);
		return false;
	}
}