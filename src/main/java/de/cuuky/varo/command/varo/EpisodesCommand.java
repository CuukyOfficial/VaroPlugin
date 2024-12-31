package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.config.language.Messages;
import de.cuuky.varo.player.VaroPlayer;

public class EpisodesCommand extends VaroCommand {

	public EpisodesCommand() {
		super("episodes", "Zeigt dir wie viele episoden du bereits gespielt hast", "varo.episodes");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if (vp == null) {
		    Messages.COMMANDS_ERROR_NO_CONSOLE.send(vp);
			return;
		}

		Messages.COMMANDS_VARO_EPISODES.send(vp);
	}

}
