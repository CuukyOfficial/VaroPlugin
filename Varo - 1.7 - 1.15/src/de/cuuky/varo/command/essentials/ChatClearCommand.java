package de.cuuky.varo.command.essentials;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.messages.language.languages.defaults.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.version.VersionUtils;

public class ChatClearCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		VaroPlayer vp = (sender instanceof Player ? VaroPlayer.getPlayer((Player) sender) : null);
		if(!sender.hasPermission("varo.chatclear")) {
			sender.sendMessage(ConfigMessages.NOPERMISSION_NO_PERMISSION.getValue(vp));
			return false;
		}

		for(int i = 0; i < 100; i++)
			for(Player pl : VersionUtils.getOnlinePlayer())
				pl.sendMessage("");

		Bukkit.broadcastMessage(Main.getPrefix() + "§7Der Chat wurde §7gecleart§7!");
		return false;
	}
}