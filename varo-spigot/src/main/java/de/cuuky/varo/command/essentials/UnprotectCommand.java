package de.cuuky.varo.command.essentials;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.language.Messages;
import de.cuuky.varo.listener.helper.cancelable.CancelableType;
import de.cuuky.varo.listener.helper.cancelable.VaroCancelable;
import de.cuuky.varo.player.VaroPlayer;
import io.github.almightysatan.slams.Placeholder;

public class UnprotectCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("varo.unprotect")) {
			Messages.COMMANDS_ERROR_PERMISSION.send(sender);
			return false;
		}

		if (args.length != 1) {
			sender.sendMessage(Main.getPrefix() + "§7/protect <Player/@a>");
			sender.sendMessage(Main.getPrefix() + "§7/unprotect <Player/@a>");
			return false;
		}

		if (args[0].equalsIgnoreCase("@a")) {
			for (VaroPlayer player : VaroPlayer.getOnlinePlayer()) {
				VaroCancelable.removeCancelable(player, CancelableType.PROTECTION);
			}

			sender.sendMessage(Main.getPrefix() + "Erfolgreich alle Spieler unprotected!");
			return false;
		}

		if (Bukkit.getPlayerExact(args[0]) == null) {
		    Messages.COMMANDS_ERROR_UNKNOWN_PLAYER.send(sender, Placeholder.constant("target", args[0]));
			return false;
		}

		Player player = Bukkit.getPlayerExact(args[0]);
		VaroPlayer target = VaroPlayer.getPlayer(player);
		VaroCancelable.removeCancelable(target, CancelableType.PROTECTION);

		sender.sendMessage(Main.getPrefix() + "§7" + args[0] + " §7erfolgreich unprotected!");
		return false;
	}
}