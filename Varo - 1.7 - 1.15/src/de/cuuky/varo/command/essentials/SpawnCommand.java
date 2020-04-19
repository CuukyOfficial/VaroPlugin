package de.cuuky.varo.command.essentials;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.messages.language.languages.defaults.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;

public class SpawnCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		VaroPlayer vp = (sender instanceof Player ? VaroPlayer.getPlayer((Player) sender) : null);
		Location loc = Main.getVaroGame().getVaroWorldHandler().getMainWorld().getWorld().getSpawnLocation();
		if (!(sender instanceof Player)) {
			if (loc == null)
				sender.sendMessage(Main.getPrefix() + "§7Main World not found!");
			sender.sendMessage(Main.getPrefix() + ConfigMessages.SPAWN_WORLD.getValue(vp).replace("%x%", loc.getBlockX() + "").replace("%y%", loc.getBlockY() + "").replace("%z%", loc.getBlockZ() + ""));
			return false;
		}

		if (args.length != 0) {
			sender.sendMessage(Main.getPrefix() + "§7/spawn");
			return false;
		}

		Player player = (Player) sender;
		loc = player.getWorld().getSpawnLocation();

		if (player.getWorld().getEnvironment() == World.Environment.THE_END) {
			sender.sendMessage(Main.getPrefix() + "§7Im Ende kann dir der Spawn nicht angegeben werden.");
			return false;
		} else if (player.getWorld().getEnvironment() == World.Environment.NETHER) {
			sender.sendMessage(Main.getPrefix() + ConfigMessages.SPAWN_NETHER.getValue(vp).replace("%x%", loc.getBlockX() + "").replace("%y%", loc.getBlockY() + "").replace("%z%", loc.getBlockZ() + ""));
			sender.sendMessage(Main.getPrefix() + ConfigMessages.SPAWN_DISTANCE_NETHER.getValue(vp).replace("%distance%", String.valueOf((int) player.getLocation().distance(loc))));
		} else {
			sender.sendMessage(Main.getPrefix() + ConfigMessages.SPAWN_WORLD.getValue(vp).replace("%x%", loc.getBlockX() + "").replace("%y%", loc.getBlockY() + "").replace("%z%", loc.getBlockZ() + ""));
			sender.sendMessage(Main.getPrefix() + ConfigMessages.SPAWN_DISTANCE.getValue(vp).replace("%distance%", String.valueOf((int) player.getLocation().distance(loc))));
		}

		return false;
	}
}