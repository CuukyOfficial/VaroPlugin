package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.config.language.Messages;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.utils.VaroUtils;
import io.github.almightysatan.slams.Placeholder;

public class RandomTeamCommand extends VaroCommand {

	public RandomTeamCommand() {
		super("randomteam", "Gibt allen Spielern, die noch kein Team haben, zufällige Teampartner", "varo.randomteam", "rt");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if (args.length != 1) {
			Messages.COMMANDS_VARO_RANDOMTEAM_HELP.send(vp);
			return;
		}

		int teamsize = 0;
		try {
			teamsize = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			Messages.COMMANDS_ERROR_NO_NUMBER.send(vp, Placeholder.constant("text", args[0]));
			return;
		}

		if (teamsize < 1) {
			Messages.COMMANDS_VARO_RANDOMTEAM_TEAMSIZE_TOO_SMALL.send(vp);
			return;
		}
		
        VaroUtils.doRandomTeam(teamsize);
		Messages.COMMANDS_VARO_RANDOMTEAM_SORTED.send(vp, Placeholder.constant("sort-size", String.valueOf(teamsize)));
	}
}