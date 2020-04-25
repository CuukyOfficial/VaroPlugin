package de.cuuky.varo.command.essentials;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;

public class FlyCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		VaroPlayer vp = (sender instanceof Player ? VaroPlayer.getPlayer((Player) sender) : null);
		if (!sender.hasPermission("varo.fly")) {
			sender.sendMessage(ConfigMessages.NOPERMISSION_NO_PERMISSION.getValue(vp));
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