package de.cuuky.varo.command.essentials;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;

public class PingCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String ping, String[] args) {
		VaroPlayer vp = (sender instanceof Player ? VaroPlayer.getPlayer((Player) sender) : null);
		if (args.length == 0) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(Main.getPrefix() + "Du musst ein Spieler sein!");
				return false;
			}

			sender.sendMessage(Main.getPrefix() + ConfigMessages.OTHER_PING.getValue(vp, vp));
		} else if (args.length == 1) {
			if (!sender.hasPermission("varo.ping")) {
				sender.sendMessage(ConfigMessages.NOPERMISSION_NO_PERMISSION.getValue(vp));
				return false;
			}

			Player p = Bukkit.getPlayerExact(args[0]);
			if (p == null) {
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + args[0] + " §7nicht gefunden!");
				return false;
			}

			sender.sendMessage(Main.getPrefix() + "§7Der Ping von " + Main.getColorCode() + args[0] + " §7betraegt " + Main.getColorCode() + String.valueOf(VaroPlayer.getPlayer(p).getNetworkManager().getPing()) + "ms§7!");
		} else
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/ping §7[Player]");

		return false;
	}
}