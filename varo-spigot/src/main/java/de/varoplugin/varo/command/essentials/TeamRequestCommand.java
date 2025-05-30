package de.varoplugin.varo.command.essentials;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;

public class TeamRequestCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Bukkit.dispatchCommand(sender, ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " tr " + String.join(" ", args));
		return true;
	}
}
