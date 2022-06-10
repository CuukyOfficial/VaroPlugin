package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.game.state.GameState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RestartCommand extends VaroCommand {
	private static final String[] subCommands = null;
	public RestartCommand() {
		super("restart", "Restartet das Projekt", "varo.restart", subCommands);
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if (Main.getVaroGame().getGameState() == GameState.LOBBY) {
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_RESTART_IN_LOBBY.getValue(vp));
			return;
		}

		Main.getVaroGame().setGamestate(GameState.LOBBY);
		sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_RESTART_RESTARTED.getValue(vp));
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