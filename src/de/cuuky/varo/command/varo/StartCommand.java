package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.game.VaroGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StartCommand extends VaroCommand {
	private static final String[] subCommands = null;
	public StartCommand() {
		super("start", "Startet das Varo", "varo.start", subCommands);
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		VaroGame game = Main.getVaroGame();
		if (game.isStarting()) {
			sender.sendMessage(Main.getPrefix() + "Das Spiel startet bereits!");
			return;
		}

		if (game.hasStarted()) {
			sender.sendMessage(Main.getPrefix() + "Das Spiel wurde bereits gestartet!");
			return;
		}

		game.prepareStart();
		sender.sendMessage(Main.getPrefix() + "Spiel erfolgreich gestartet!");
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