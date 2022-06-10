package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EpisodesCommand extends VaroCommand {
	public EpisodesCommand() {
		super("episodes", "Zeigt dir wie viele episoden du bereits gespielt hast", "varo.episodes", null);
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if (vp == null) {
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_ERROR_NO_CONSOLE.getValue(vp));
			return;
		}

		sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_EPISODES.getValue(vp, vp));
	}
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		return new ArrayList<>();
	}
}
