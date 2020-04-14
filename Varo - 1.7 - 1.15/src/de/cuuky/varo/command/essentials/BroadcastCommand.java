package de.cuuky.varo.command.essentials;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.messages.language.languages.LanguageDE;
import de.cuuky.varo.entity.player.VaroPlayer;

public class BroadcastCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		VaroPlayer vp = (sender instanceof Player ? VaroPlayer.getPlayer((Player) sender) : null);
		if(!sender.hasPermission("varo.broadcast")) {
			sender.sendMessage(Main.getLanguageManager().getValue(LanguageDE.NOPERMISSION_NO_PERMISSION, vp));
			return false;
		}

		if(args.length == 0) {
			sender.sendMessage(Main.getPrefix() + "§7/bc <Message>");
			return false;
		}

		String msg = "";
		for(String arg : args)
			if(!msg.equals(""))
				msg = msg + " " + arg;
			else
				msg = arg;

		Bukkit.broadcastMessage("§7[§cBroadcast§7] §c" + msg.replace("&", "§"));
		return false;
	}

}