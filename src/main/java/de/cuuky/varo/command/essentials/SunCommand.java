package de.cuuky.varo.command.essentials;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.config.language.Messages;

public class SunCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender.hasPermission("varo.sun"))) {
			Messages.COMMANDS_ERROR_PERMISSION.send(sender);
			return false;
		}

		World world = sender instanceof Player ? ((Player) sender).getWorld() : Bukkit.getWorlds().get(0);

		world.setStorm(false);
		world.setThundering(false);
		Messages.COMMANDS_WEATHER_SUN.send(sender);
		return false;
	}

}