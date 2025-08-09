package de.varoplugin.varo.command.essentials;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.varoplugin.varo.Main;
import de.varoplugin.varo.config.language.Messages;

public class HealCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("varo.heal")) {
			Messages.COMMANDS_ERROR_PERMISSION.send(sender);
			return false;
		}

		if (args.length == 0) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(Main.getPrefix() + "§7/heal [Player/@a]");
				return false;
			}

			Player p = (Player) sender;
			p.setHealth(p.getMaxHealth());
			p.getActivePotionEffects().forEach(effect -> p.removePotionEffect(effect.getType()));
			p.setFoodLevel(20);
            Bukkit.broadcastMessage("§a" + p.getName() + " §7hat sich geheilt!");
		} else if (args.length == 1) {
			if (Bukkit.getPlayerExact(args[0]) == null) {
				sender.sendMessage(Main.getPrefix() + "§7" + args[0] + " §7nicht gefunden!");
				return false;
			}

			if (args[0].equalsIgnoreCase("@a")) {
				for (Player p : Bukkit.getOnlinePlayers()) {
					p.setHealth(p.getMaxHealth());
					p.getActivePotionEffects().forEach(effect -> p.removePotionEffect(effect.getType()));
					p.setFoodLevel(20);
				}
                Bukkit.broadcastMessage("§aAlle Spieler §7wurden geheilt!");
				return false;
			}

			Player p = Bukkit.getPlayerExact(args[0]);
			p.setHealth(p.getMaxHealth());
			p.getActivePotionEffects().forEach(effect -> p.removePotionEffect(effect.getType()));
			p.setFoodLevel(20);
            Bukkit.broadcastMessage("§a" + p.getName() + " §7wurde von §a" + sender.getName() + "§7 geheilt!");
		} else
			sender.sendMessage(Main.getPrefix() + "§7/heal [Player]");
		return false;
	}

}