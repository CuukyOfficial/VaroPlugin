package de.cuuky.varo.command.essentials;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.messages.language.languages.defaults.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;

public class HealCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		VaroPlayer vp = (sender instanceof Player ? VaroPlayer.getPlayer((Player) sender) : null);
		if (!sender.hasPermission("varo.heal")) {
			sender.sendMessage(ConfigMessages.NOPERMISSION_NO_PERMISSION.getValue(vp));
			return false;
		}

		if (args.length == 0) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(Main.getPrefix() + "§7/heal [Player/@a]");
				return false;
			}

			Player p = (Player) sender;
			p.setHealth(20);
			p.getActivePotionEffects().forEach(effect -> p.removePotionEffect(effect.getType()));
			p.setFoodLevel(20);
			sender.sendMessage(Main.getPrefix() + "Du wurdest erfolgreich §ageheilt§7!");
		} else if (args.length == 1) {
			if (Bukkit.getPlayerExact(args[0]) == null) {
				sender.sendMessage(Main.getPrefix() + "§7" + args[0] + " §7nicht gefunden!");
				return false;
			}

			if (args[0].equalsIgnoreCase("@a")) {
				for (Player p : Bukkit.getOnlinePlayers()) {
					p.setHealth(20);
					p.getActivePotionEffects().forEach(effect -> p.removePotionEffect(effect.getType()));
					p.setFoodLevel(20);
				}
				sender.sendMessage(Main.getPrefix() + "§aAlle Spieler §7erfolgreich geheilt!");
				return false;
			}

			Player p = Bukkit.getPlayerExact(args[0]);
			p.setHealth(20);
			p.getActivePotionEffects().forEach(effect -> p.removePotionEffect(effect.getType()));
			p.setFoodLevel(20);
			sender.sendMessage(Main.getPrefix() + "§a" + args[0] + " §7erfolgreich geheilt!");
		} else
			sender.sendMessage(Main.getPrefix() + "§7/heal [Player]");
		return false;
	}

}