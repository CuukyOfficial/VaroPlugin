package de.varoplugin.varo.command.essentials;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.varoplugin.varo.Main;
import de.varoplugin.varo.config.language.Messages;
import de.varoplugin.varo.player.VaroPlayer;

public class FlyCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("varo.fly")) {
			Messages.COMMANDS_ERROR_PERMISSION.send(sender);
			return false;
		}

		if (args.length == 0) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(Main.getPrefix() + "§7Entweder /fly [Player/@a] oder Spieler sein!");
				return false;
			}

			Player p = (Player) sender;
			p.setAllowFlight(true);
			p.setFlying(true);
			sender.sendMessage(Main.getPrefix() + "§7Du kannst jetzt §afliegen§7!");
		} else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("@a")) {
				for (VaroPlayer player : VaroPlayer.getOnlinePlayer()) {
					player.getPlayer().setAllowFlight(true);
					player.getPlayer().setFlying(true);
				}

				sender.sendMessage(Main.getPrefix() + "Jeder kann jetzt fliegen!");
				return false;
			}

			Player to = Bukkit.getPlayerExact(args[0]);
			if (to == null) {
				sender.sendMessage(Main.getPrefix() + "§7" + args[0] + "§7 nicht gefunden!");
				return false;
			}

			to.setAllowFlight(true);
			to.setFlying(true);
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + to.getName() + " §7kann jetzt §afliegen§7!");
		} else {
			sender.sendMessage(Main.getPrefix() + "§7/fly [Player/@a]");
			sender.sendMessage(Main.getPrefix() + "§7/unfly [Player/@a]");
		}
		return false;
	}
}