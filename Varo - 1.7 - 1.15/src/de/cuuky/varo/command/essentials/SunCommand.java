package de.cuuky.varo.command.essentials;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.messages.ConfigMessages;

public class SunCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender.hasPermission("varo.sun"))) {
			sender.sendMessage(ConfigMessages.NOPERMISSION_NO_PERMISSION.getValue());
			return false;
		}

		World world = sender instanceof Player ? ((Player) sender).getWorld() : Bukkit.getWorlds().get(0);

		world.setStorm(false);
		world.setThundering(false);
		sender.sendMessage(Main.getPrefix() + "Wechsle zu schoenem Wetter...");
		return false;
	}

}