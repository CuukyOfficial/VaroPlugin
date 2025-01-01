package de.cuuky.varo.command.essentials;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.language.Messages;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.player.VaroPlayer;

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
				sender.sendMessage(ConfigMessages.NOPERMISSION_NO_PERMISSION.getValue(vp));
				return false;
			}

			VaroPlayer target = VaroPlayer.getPlayer(args[0]);
			if (target == null || !target.isOnline()) {
				Messages.COMMANDS_ERROR_UNKNOWN_PLAYER.send(sender);
				return false;
			}

			Messages.COMMANDS_PING.send(sender, target);
		} else
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/ping §7[player]");

		return false;
	}
}