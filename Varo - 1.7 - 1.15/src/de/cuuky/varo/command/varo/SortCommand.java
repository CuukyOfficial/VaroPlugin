package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.configuration.configurations.messages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.spawns.sort.PlayerSort;
import de.cuuky.varo.spawns.sort.PlayerSort.SortResult;

public class SortCommand extends VaroCommand {

	public SortCommand() {
		super("sort", "Sortiert die Spieler in ihre Löcher", "varo.sort");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if(args.length != 0) {
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_SORT_HELP.getValue());
			return;
		}

		SortResult result = new PlayerSort().sortPlayers();
		switch(result) {
		case SORTED_WELL:
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_SORT_SORTED_WELL.getValue());
			break;
		case NO_SPAWN_WITH_TEAM:
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_SORT_NO_SPAWN_WITH_TEAM.getValue());
			break;
		case NO_SPAWN:
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_SORT_NO_SPAWN.getValue());
			break;
		}
	}
}