package de.cuuky.varo.command.essentials;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.messages.language.languages.defaults.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;

public class InvSeeCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		VaroPlayer vp = (sender instanceof Player ? VaroPlayer.getPlayer((Player) sender) : null);
		if(!sender.hasPermission("varo.invsee")) {
			sender.sendMessage(ConfigMessages.NOPERMISSION_NO_PERMISSION.getValue(vp));
			return false;
		}

		if(args.length != 1) {
			sender.sendMessage(Main.getPrefix() + "§7/Invsee <Player>");
			return false;
		}

		if(!(sender instanceof Player)) {
			sender.sendMessage(Main.getPrefix() + "Nicht für die Konsole!");
			return false;
		}

		if(Bukkit.getPlayerExact(args[0]) == null) {
			sender.sendMessage(Main.getPrefix() + "§7" + args[0] + " §7nicht gefunden!");
			return false;
		}

		Player p = (Player) sender;
		p.openInventory(Bukkit.getPlayerExact(args[0]).getInventory());
		return false;
	}

}