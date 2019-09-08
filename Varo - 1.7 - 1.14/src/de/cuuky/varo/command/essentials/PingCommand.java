package de.cuuky.varo.command.essentials;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.config.messages.ConfigMessages;
import de.cuuky.varo.player.VaroPlayer;

public class PingCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String ping, String[] args) {
		if(args.length == 0) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(Main.getPrefix() + "Du musst ein Spieler sein!");
				return false;
			}

			sender.sendMessage(Main.getPrefix() + ConfigMessages.COMMAND_PING.getValue().replaceAll("%ping%", String.valueOf(VaroPlayer.getPlayer((Player) sender).getNetworkManager().getPing())));
		} else if(args.length == 1) {
			if(!sender.hasPermission("varo.ping")) {
				sender.sendMessage(VaroCommand.getNoPermission("varo.ping"));
				return false;
			}

			Player p = Bukkit.getPlayerExact(args[0]);
			if(p == null) {
				sender.sendMessage(Main.getPrefix() + ConfigEntry.PROJECTNAME_COLORCODE.getValueAsString() + args[0] + " §7nicht gefunden!");
				return false;
			}

			sender.sendMessage(Main.getPrefix() + "§7Der Ping von " + ConfigEntry.PROJECTNAME_COLORCODE.getValueAsString() + args[0] + " §7beträgt " + ConfigEntry.PROJECTNAME_COLORCODE.getValueAsString() + String.valueOf(VaroPlayer.getPlayer(p).getNetworkManager().getPing()) + "ms§7!");
		} else
			sender.sendMessage(Main.getPrefix() + "§b/ping §7[Player]");

		return false;
	}
}