package de.varoplugin.varo.command.essentials;

import io.github.almightysatan.slams.Placeholder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.varoplugin.varo.Main;
import de.varoplugin.varo.config.language.Messages;
import de.varoplugin.varo.player.VaroPlayer;

public class PingCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String ping, String[] args) {
		VaroPlayer vp = (sender instanceof Player ? VaroPlayer.getPlayer((Player) sender) : null);
		if (args.length == 0) {
			if (!(sender instanceof Player)) {
				Messages.COMMANDS_ERROR_NO_CONSOLE.send(sender);
				return false;
			}

			Messages.COMMANDS_PING.send(vp);
		} else if (args.length == 1) {
			if (!sender.hasPermission("varo.ping")) {
				Messages.COMMANDS_ERROR_PERMISSION.send(sender);
				return false;
			}

			VaroPlayer target = VaroPlayer.getPlayer(args[0]);
			if (target == null || !target.isOnline()) {
				Messages.COMMANDS_ERROR_UNKNOWN_PLAYER.send(sender, Placeholder.constant("target", args[0]));
				return false;
			}

			Messages.COMMANDS_PING.send(sender, target);
		} else
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/ping §7[player]");

		return false;
	}
}