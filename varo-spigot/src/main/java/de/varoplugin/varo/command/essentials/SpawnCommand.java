package de.varoplugin.varo.command.essentials;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.varoplugin.varo.config.language.Messages;

public class SpawnCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		if (!(sender instanceof Player)) {
		    Messages.COMMANDS_SPAWN_OVERWORLD.send(sender);
		    return false;
		}

		Player player = (Player) sender;
		switch (player.getWorld().getEnvironment()) {
		    case NORMAL:
		        Messages.COMMANDS_SPAWN_OVERWORLD.send(player);
		        break;
		    case NETHER:
		        Messages.COMMANDS_SPAWN_NETHER.send(player);
		        break;
		    case THE_END:
		        Messages.COMMANDS_SPAWN_END.send(player);
		        break;
		}
		return false;
	}
}