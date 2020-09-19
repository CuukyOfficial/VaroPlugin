package de.cuuky.varo.command.essentials;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.cfw.version.ServerSoftware;
import de.cuuky.cfw.version.VersionUtils;
import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.utils.ModUtils;

public class InfoCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		VaroPlayer vp = (sender instanceof Player ? VaroPlayer.getPlayer((Player) sender) : null);
		if (!sender.hasPermission("varo.info")) {
			sender.sendMessage(ConfigMessages.NOPERMISSION_NO_PERMISSION.getValue(vp));
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
		sender.sendMessage(Main.getPrefix() + "Leben: " + Main.getColorCode() + VersionUtils.getHearts(player) + "§7/20.0");
		sender.sendMessage(Main.getPrefix() + "Hunger: " + Main.getColorCode() + player.getFoodLevel() + "§7/20.0");
		sender.sendMessage(Main.getPrefix() + "Level: " + Main.getColorCode() + player.getLevel());
		sender.sendMessage(Main.getPrefix() + "Location: x:" + Main.getColorCode() + player.getLocation().getBlockX() + "§7, y:" + Main.getColorCode() + player.getLocation().getBlockY() + "§7, z:" + Main.getColorCode() + player.getLocation().getBlockZ());
		sender.sendMessage(Main.getPrefix() + "IP: " + Main.getColorCode() + player.getAddress().getAddress().toString());
		if(VersionUtils.getServerSoftware() == ServerSoftware.MAGMA)
			sender.sendMessage(Main.getPrefix() + "Mods: " + Main.getColorCode() + ModUtils.getModListString(player));
		return false;
	}
}