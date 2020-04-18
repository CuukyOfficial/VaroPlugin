package de.cuuky.varo.command.essentials;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.messages.language.languages.defaults.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;

public class RainCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		VaroPlayer vp = (sender instanceof Player ? VaroPlayer.getPlayer((Player) sender) : null);
		if(!(sender.hasPermission("varo.rain"))) {
			sender.sendMessage(ConfigMessages.NOPERMISSION_NO_PERMISSION.getValue(vp));
			return false;
		}

		World world = sender instanceof Player ? ((Player) sender).getWorld() : Bukkit.getWorlds().get(0);

		world.setStorm(true);
		world.setThundering(false);
		sender.sendMessage(Main.getPrefix() + "Wechsle zu Regen...");
		return false;
	}
}