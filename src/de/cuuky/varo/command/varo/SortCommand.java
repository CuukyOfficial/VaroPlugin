package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.spawns.sort.PlayerSort;
import de.cuuky.varo.spawns.sort.PlayerSort.SortResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SortCommand extends VaroCommand {
	private static final String[] subCommands = null;
	public SortCommand() {
		super("sort", "Sortiert die Spieler in ihre Loecher", "varo.sort", subCommands);
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if (args.length != 0) {
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_SORT_HELP.getValue(vp));
			return;
		}

		SortResult result = new PlayerSort().sortPlayers();
		switch (result) {
		case SORTED_WELL:
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_SORT_SORTED_WELL.getValue(vp));
			break;
		case NO_SPAWN_WITH_TEAM:
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_SORT_NO_SPAWN_WITH_TEAM.getValue(vp));
			break;
		case NO_SPAWN:
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_SORT_NO_SPAWN.getValue(vp));
			break;
		}
	}
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		ArrayList<String> list = new ArrayList<>();
		if (args.length == 2 && subCommands != null) {
			List<String> subCommands = Arrays.asList(this.subCommands);
			list.addAll(subCommands);
		}
		ArrayList<String> completerList = new ArrayList<>();
		String curentarg = args[args.length - 1].toLowerCase();
		for (String s : list) {
			String s1 = s.toLowerCase();
			if (s1.startsWith(curentarg)) {
				completerList.add(s);
			}
		}
		return completerList;
	}
}