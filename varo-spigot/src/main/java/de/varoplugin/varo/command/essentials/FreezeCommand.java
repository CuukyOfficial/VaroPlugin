package de.varoplugin.varo.command.essentials;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.varoplugin.varo.Main;
import de.varoplugin.varo.config.language.Messages;
import de.varoplugin.varo.listener.helper.cancelable.CancelableType;
import de.varoplugin.varo.listener.helper.cancelable.VaroCancelable;
import de.varoplugin.varo.player.VaroPlayer;

public class FreezeCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("varo.freeze")) {
			Messages.COMMANDS_ERROR_PERMISSION.send(sender);
			return false;
		}

		if (args.length != 1) {
			sender.sendMessage(Main.getPrefix() + "§7/freeze <Player/@a>");
			sender.sendMessage(Main.getPrefix() + "§7/unfreeze <Player/@a>");
			return false;
		}

		if (args[0].equalsIgnoreCase("@a")) {
			for (VaroPlayer player : VaroPlayer.getOnlinePlayer()) {
				if (player.getPlayer().isOp()) {
					continue;
				}
				new VaroCancelable(CancelableType.FREEZE, player);
			}

			sender.sendMessage(Main.getPrefix() + "Erfolgreich alle Spieler gefreezed!");
			return false;
		}

		if (Bukkit.getPlayerExact(args[0]) == null) {
			sender.sendMessage(Main.getPrefix() + "§7" + args[0] + " §7nicht gefunden!");
			return false;
		}

		Player player = Bukkit.getPlayerExact(args[0]);
		VaroPlayer target = VaroPlayer.getPlayer(player);
		if (player.isOp()) {
			sender.sendMessage(Main.getPrefix() + "Ein Admin kann nicht gefreezed werden!");
			return false;
		}

		new VaroCancelable(CancelableType.FREEZE, target);

		sender.sendMessage(Main.getPrefix() + "§7" + args[0] + " §7erfolgreich gefreezed!");
		return false;
	}
}