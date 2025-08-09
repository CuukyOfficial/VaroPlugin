package de.varoplugin.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.varoplugin.varo.command.VaroCommand;
import de.varoplugin.varo.config.language.Messages;
import de.varoplugin.varo.player.VaroPlayer;
import de.varoplugin.varo.spawns.sort.PlayerSort;
import de.varoplugin.varo.spawns.sort.PlayerSort.SortResult;

public class SortCommand extends VaroCommand {

	public SortCommand() {
		super("sort", "Sortiert die Spieler in ihre Löcher", "varo.sort");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if (args.length != 0) {
			Messages.COMMANDS_VARO_SORT_HELP.send(sender);
			return;
		}

		SortResult result = new PlayerSort().sortPlayers();
		switch (result) {
		case SORTED_WELL:
			Messages.COMMANDS_VARO_SORT_SORTED_WELL.send(sender);
			break;
		case NO_SPAWN_WITH_TEAM:
			Messages.COMMANDS_VARO_SORT_NO_SPAWN_WITH_TEAM.send(sender);
			break;
		case NO_SPAWN:
		    Messages.COMMANDS_VARO_SORT_NO_SPAWN.send(sender);
			break;
		}
	}
}