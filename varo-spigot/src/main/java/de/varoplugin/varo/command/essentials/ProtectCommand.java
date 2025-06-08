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
import io.github.almightysatan.slams.Placeholder;

public class ProtectCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("varo.protect")) {
			Messages.COMMANDS_ERROR_PERMISSION.send(sender);
			return false;
		}

		if (args.length != 1) {
			sender.sendMessage(Main.getPrefix() + "§7/protect <Player/@a>");
			sender.sendMessage(Main.getPrefix() + "§7/unprotect <Player/@a>");
			return false;
		}

		if (args[0].equalsIgnoreCase("@a")) {
			for (VaroPlayer player : VaroPlayer.getOnlinePlayer())
				new VaroCancelable(CancelableType.PROTECTION, player);

			sender.sendMessage(Main.getPrefix() + "Erfolgreich alle Spieler protected!");
			return false;
		}

		if (Bukkit.getPlayerExact(args[0]) == null) {
		    Messages.COMMANDS_ERROR_UNKNOWN_PLAYER.send(sender, Placeholder.constant("target", args[0]));
			return false;
		}

		Player player = Bukkit.getPlayerExact(args[0]);
		VaroPlayer target = VaroPlayer.getPlayer(player);
		new VaroCancelable(CancelableType.PROTECTION, target);

		sender.sendMessage(Main.getPrefix() + "§7" + args[0] + " §7erfolgreich protected!");
		return false;
	}
}