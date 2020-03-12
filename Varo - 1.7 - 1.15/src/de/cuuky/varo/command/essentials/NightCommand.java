package de.cuuky.varo.command.essentials;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.messages.ConfigMessages;

public class NightCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("varo.night")) {
			sender.sendMessage(ConfigMessages.NOPERMISSION_NO_PERMISSION.getValue());
			return false;
		}

		World world = sender instanceof Player ? ((Player) sender).getWorld() : Main.getVaroGame().getVaroWorld().getWorld();
		world.setTime(13000);
		sender.sendMessage(Main.getPrefix() + "Es ist jetzt " + Main.getColorCode() + "Nacht§7!");
		return false;
	}
}