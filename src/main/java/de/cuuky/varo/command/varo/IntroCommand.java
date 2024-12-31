package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.config.language.Messages;
import de.cuuky.varo.game.start.SuroStart;
import de.cuuky.varo.player.VaroPlayer;

public class IntroCommand extends VaroCommand {

	private SuroStart suroStart;

	public IntroCommand() {
		super("intro", "Startet das SURO Intro", "varo.intro");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if (suroStart != null) {
			Messages.COMMANDS_VARO_INTRO_ALREADY_STARTED.send(vp);
			return;
		}

		if (Main.getVaroGame().hasStarted()) {
		    Messages.COMMANDS_VARO_INTRO_GAME_ALREADY_STARTED.send(vp);
			return;
		}

		suroStart = new SuroStart();
		Messages.COMMANDS_VARO_INTRO_STARTED.send(vp);
	}
}