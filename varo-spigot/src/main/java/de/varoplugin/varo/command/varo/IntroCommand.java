package de.varoplugin.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.varoplugin.varo.Main;
import de.varoplugin.varo.command.VaroCommand;
import de.varoplugin.varo.config.language.Messages;
import de.varoplugin.varo.game.start.SuroStart;
import de.varoplugin.varo.player.VaroPlayer;

public class IntroCommand extends VaroCommand {

	private SuroStart suroStart;

	public IntroCommand() {
		super("intro", "Startet das SURO Intro", "varo.intro");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if (suroStart != null) {
			Messages.COMMANDS_VARO_INTRO_ALREADY_STARTED.send(sender);
			return;
		}

		if (Main.getVaroGame().hasStarted()) {
		    Messages.COMMANDS_VARO_INTRO_GAME_ALREADY_STARTED.send(sender);
			return;
		}

		suroStart = new SuroStart();
		Messages.COMMANDS_VARO_INTRO_STARTED.send(sender);
	}
}