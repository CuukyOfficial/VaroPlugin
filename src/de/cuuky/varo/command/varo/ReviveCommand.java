package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.stats.stat.PlayerState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReviveCommand extends VaroCommand {
	private static final String[] subCommands = null;
	public ReviveCommand() {
		super("revive", "Belebt einen Spieler wieder", "varo.revive", subCommands, "unkill");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + label + " <Spieler> - Belebt einen Spieler wieder");
			return;
		}

		VaroPlayer target = VaroPlayer.getPlayer(args[0]);
		if (target == null) {
			sender.sendMessage(Main.getPrefix() + "Spieler konnte nicht gefunden werden!");
			return;
		}

		if (target.getStats().isAlive()) {
			sender.sendMessage(Main.getPrefix() + "Dieser Spieler lebt bereits!");
			return;
		}

		target.getStats().setState(PlayerState.ALIVE);

		sender.sendMessage(Main.getPrefix() + "Spieler erfolgreich wiederbelebt!");
	}
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		ArrayList<String> list = new ArrayList<>();
		if (args.length == 2) {
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