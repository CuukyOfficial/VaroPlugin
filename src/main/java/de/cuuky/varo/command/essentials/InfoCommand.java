package de.cuuky.varo.command.essentials;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.language.Messages;
import de.cuuky.varo.utils.MagmaUtils;
import de.varoplugin.cfw.version.ServerSoftware;
import de.varoplugin.cfw.version.VersionUtils;

public class InfoCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("varo.info")) {
			Messages.COMMANDS_ERROR_PERMISSION.send(sender);
			return false;
		}

		if (args.length == 0) {
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/info §7<Spieler>");
			return false;
		}

		Player player = Bukkit.getPlayerExact(args[0]);
		if (player == null) {
			sender.sendMessage(Main.getPrefix() + "Spieler nicht gefunden!");
			return false;
		}

		sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "§l" + player.getName() + "§7:");
		sender.sendMessage(Main.getPrefix() + "Leben: " + Main.getColorCode() + player .getHealth()+ "§7/20.0");
		sender.sendMessage(Main.getPrefix() + "Hunger: " + Main.getColorCode() + player.getFoodLevel() + "§7/20.0");
		sender.sendMessage(Main.getPrefix() + "Level: " + Main.getColorCode() + player.getLevel());
		sender.sendMessage(Main.getPrefix() + "Location: x:" + Main.getColorCode() + player.getLocation().getBlockX() + "§7, y:" + Main.getColorCode() + player.getLocation().getBlockY() + "§7, z:" + Main.getColorCode() + player.getLocation().getBlockZ());
		sender.sendMessage(Main.getPrefix() + "IP: " + Main.getColorCode() + player.getAddress().getAddress().toString());
		if (VersionUtils.getServerSoftware() == ServerSoftware.MAGMA_1_12)
			sender.sendMessage(Main.getPrefix() + "Mods: " + Main.getColorCode() + MagmaUtils.getModListString(player));
		return false;
	}
}