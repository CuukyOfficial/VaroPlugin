package de.cuuky.varo.command.essentials;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.app.Main;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.vanish.Vanish;

public class VanishCommand implements CommandExecutor {

	public static ArrayList<String> vanished = new ArrayList<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		VaroPlayer vp = (sender instanceof Player ? VaroPlayer.getPlayer((Player) sender) : null);
		if (!sender.hasPermission("varo.vanish")) {
			sender.sendMessage(ConfigMessages.NOPERMISSION_NO_PERMISSION.getValue(vp));
			return false;
		}

		if (args.length == 0) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(Main.getPrefix() + "Not for console. Type /vanish [Player]");
				return false;
			}

			Vanish v = Vanish.getVanish((Player) sender);
			if (v == null) {
				v = new Vanish((Player) sender);
				sender.sendMessage(Main.getPrefix() + "Du bist nun " + Main.getColorCode() + "gevanished§7!");
				return false;
			}

			v.remove();
			sender.sendMessage(Main.getPrefix() + "Du bist nun " + Main.getColorCode() + "unvanished§7!");
		} else if (args.length == 1) {
			Player player = Bukkit.getPlayerExact(args[0]);
			if (player == null) {
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + args[0] + " §7nicht gefunden!");
				return false;
			}

			Vanish v = Vanish.getVanish(player);
			if (v == null) {
				v = new Vanish(player);
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + player.getName() + " §7erfolgreich gevanished!");
				player.sendMessage(Main.getPrefix() + "Du bist nun " + Main.getColorCode() + "gevanished§7!");
				return false;
			}

			v.remove();
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + player.getName() + " §7erfolgreich unvanished!");
			player.sendMessage(Main.getPrefix() + "Du bist nun " + Main.getColorCode() + "unvanished§7!");
		} else
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/vanish §7[Spieler]");
		return false;
	}
}