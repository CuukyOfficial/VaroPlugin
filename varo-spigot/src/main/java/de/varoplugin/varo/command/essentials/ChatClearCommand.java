package de.varoplugin.varo.command.essentials;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.varoplugin.cfw.version.VersionUtils;
import de.varoplugin.varo.config.language.Messages;

public class ChatClearCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("varo.chatclear")) {
			Messages.COMMANDS_ERROR_PERMISSION.send(sender);
			return false;
		}

		for (int i = 0; i < 100; i++)
			for (Player pl : VersionUtils.getVersionAdapter().getOnlinePlayers())
				pl.sendMessage("");

		Messages.COMMANDS_CHATCLEAR_CLEAR.broadcast();
		return false;
	}
}