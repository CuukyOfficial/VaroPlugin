package de.varoplugin.varo.command.essentials;

import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.varoplugin.varo.Main;

public class MessageCommand implements CommandExecutor {

	public static HashMap<String, String> lastChat = new HashMap<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// VaroPlayer vp = (sender instanceof Player ?
		// VaroPlayer.getPlayer((Player) sender) : null);
		if (args.length == 0) {
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/msg §7<Player> <Message>");
			return false;
		}

		if (!Main.getVaroGame().hasStarted() && !sender.hasPermission("varo.setup")) {
			sender.sendMessage(Main.getPrefix() + "Du kannst nicht als nicht-Admin vor dem " + Main.getProjectName() + "-Start §7privat schreiben.");
			return false;
		}

		Player to = Bukkit.getPlayerExact(args[0]);
		if (to == null) {
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + args[0] + " §7nicht gefunden!");
			return false;
		}

		if (sender.getName().equals(to.getName())) {
			sender.sendMessage(Main.getPrefix() + "Nein.");
			return false;
		}

		String message = Arrays.stream(args).skip(1).collect(Collectors.joining(" "));
		to.sendMessage(Main.getColorCode() + sender.getName() + " §8-> §7Dir§8: §f" + message);
		sender.sendMessage("§7Du §8-> " + Main.getColorCode() + to.getName() + "§8: §f" + message);
		if (MessageCommand.lastChat.containsKey(to.getName()))
			MessageCommand.lastChat.remove(to.getName());

		lastChat.put(to.getName(), sender.getName());
		return false;
	}
}