package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.player.VaroPlayer;

public class RestartCommand extends VaroCommand {

	public RestartCommand() {
		super("restart", "Restartet das Projekt", "varo.restart");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if (!Main.getVaroGame().hasStarted()) {
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_RESTART_IN_LOBBY.getValue(vp));
			return;
		}

		Main.getVaroGame().restart();
		sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_RESTART_RESTARTED.getValue(vp));
	}

}