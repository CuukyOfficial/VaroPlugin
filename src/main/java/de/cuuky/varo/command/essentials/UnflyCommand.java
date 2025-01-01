package de.cuuky.varo.command.essentials;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.language.Messages;
import de.cuuky.varo.player.VaroPlayer;
import io.github.almightysatan.slams.Placeholder;

public class UnflyCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("varo.unfly")) {
			Messages.COMMANDS_ERROR_PERMISSION.send(sender);
			return false;
		}

		if (args.length == 0) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(Main.getPrefix() + "§7Entweder /unfly [Player/@a] oder Spieler sein!");
				return false;
			}

			Player p = (Player) sender;
			p.setAllowFlight(false);
			p.setFlying(false);
			sender.sendMessage(Main.getPrefix() + "§7Du kannst jetzt nicht mehr fliegen!");
		} else if (args.length == 1) {

			if (args[0].equalsIgnoreCase("@a")) {
				for (VaroPlayer player : VaroPlayer.getOnlinePlayer()) {
					player.getPlayer().setAllowFlight(false);
					player.getPlayer().setFlying(false);
				}

				sender.sendMessage(Main.getPrefix() + "Niemand kann mehr fliegen!");
				return false;
			}

			Player to = Bukkit.getPlayerExact(args[0]);
			if (to == null) {
				Messages.COMMANDS_ERROR_UNKNOWN_PLAYER.send(sender, Placeholder.constant("target", args[0]));
				return false;
			}

			to.setAllowFlight(false);
			to.setFlying(false);
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + to.getName() + " §7kann jetzt nicht mehr fliegen!");
		} else {
			sender.sendMessage(Main.getPrefix() + "§7/fly [Player/@a]");
			sender.sendMessage(Main.getPrefix() + "§7/unfly [Player/@a]");
		}

		return false;
	}
}