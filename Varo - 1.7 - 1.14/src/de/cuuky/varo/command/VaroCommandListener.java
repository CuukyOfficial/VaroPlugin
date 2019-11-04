package de.cuuky.varo.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.utils.Utils;

public class VaroCommandListener implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length < 1) {
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "§lVaro §7§lCommands:");
			for(VaroCommand command : VaroCommand.getVaroCommand())
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo " + command.getName() + "§8: §7" + command.getDescription());
			return false;
		}

		VaroCommand command = VaroCommand.getCommand(args[0]);
		if(command == null) {
			sender.sendMessage(Main.getPrefix() + "§7Kommando '" + Main.getColorCode() + args[0] + "§7' nicht gefunden!");
			return false;
		}

		if(command.getPermission() != null && !sender.hasPermission(command.getPermission())) {
			sender.sendMessage(VaroCommand.getNoPermission(command.getPermission()));
			return false;
		}

		command.onCommand(sender, (sender instanceof Player ? VaroPlayer.getPlayer((Player) sender) : null), cmd, label, Utils.removeString(args, 0));
		return true;
	}
}