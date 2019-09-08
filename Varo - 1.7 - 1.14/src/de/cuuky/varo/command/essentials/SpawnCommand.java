package de.cuuky.varo.command.essentials;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.messages.ConfigMessages;

public class SpawnCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		Location loc = Main.getDataManager().getWorldHandler().getWorld().getSpawnLocation();
		if(!(sender instanceof Player)) {
			if(loc == null)
				sender.sendMessage(Main.getPrefix() + "§7Main World not found!");
			sender.sendMessage(Main.getPrefix() + ConfigMessages.COMMAND_SPAWN.getValue().replaceAll("%x%", loc.getBlockX() + "").replaceAll("%y%", loc.getBlockY() + "").replaceAll("%z%", loc.getBlockZ() + ""));
			return false;
		}

		if(args.length != 0) {
			sender.sendMessage(Main.getPrefix() + "§7/spawn");
			return false;
		}

		Player player = (Player) sender;
		loc = player.getWorld().getSpawnLocation();
		sender.sendMessage(Main.getPrefix() + ConfigMessages.COMMAND_SPAWN.getValue().replaceAll("%x%", loc.getBlockX() + "").replaceAll("%y%", loc.getBlockY() + "").replaceAll("%z%", loc.getBlockZ() + ""));
		sender.sendMessage(Main.getPrefix() + ConfigMessages.COMMAND_SPAWN_DISTANCE.getValue().replace("%distance%", String.valueOf((int) player.getLocation().distance(loc))));
		return false;
	}

}
