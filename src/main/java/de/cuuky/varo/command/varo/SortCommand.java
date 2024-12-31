package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.config.language.Messages;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.spawns.sort.PlayerSort;
import de.cuuky.varo.spawns.sort.PlayerSort.SortResult;

public class SortCommand extends VaroCommand {

	public SortCommand() {
		super("sort", "Sortiert die Spieler in ihre Löcher", "varo.sort");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if (args.length != 0) {
			Messages.COMMANDS_VARO_SORT_HELP.send(vp);
			return;
		}

		SortResult result = new PlayerSort().sortPlayers();
		switch (result) {
		case SORTED_WELL:
			Messages.COMMANDS_VARO_SORT_SORTED_WELL.send(vp);
			break;
		case NO_SPAWN_WITH_TEAM:
			Messages.COMMANDS_VARO_SORT_NO_SPAWN_WITH_TEAM.send(vp);
			break;
		case NO_SPAWN:
		    Messages.COMMANDS_VARO_SORT_NO_SPAWN.send(vp);
			break;
		}
	}
}