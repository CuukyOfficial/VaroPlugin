package de.cuuky.varo.command.essentials;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.language.Messages;
import io.github.almightysatan.slams.Placeholder;

public class InvSeeCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("varo.invsee")) {
			Messages.COMMANDS_ERROR_PERMISSION.send(sender);
			return false;
		}

		if (args.length != 1) {
			sender.sendMessage(Main.getPrefix() + "§7/Invsee <Player>");
			return false;
		}

		if (!(sender instanceof Player)) {
			Messages.COMMANDS_ERROR_NO_CONSOLE.send(sender);
			return false;
		}

		if (Bukkit.getPlayerExact(args[0]) == null) {
		    Messages.COMMANDS_ERROR_UNKNOWN_PLAYER.send(sender, Placeholder.constant("target", args[0]));
			return false;
		}

		Player p = (Player) sender;
		p.openInventory(Bukkit.getPlayerExact(args[0]).getInventory());
		return false;
	}

}