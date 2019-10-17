package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.world.PlayerSort;

public class SortCommand extends VaroCommand {

	public SortCommand() {
		super("sort", "Sortiert die Spieler in ihre Löcher", "varo.sort");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if (args.length != 0) {
			sender.sendMessage(Main.getPrefix() + "§7/sort");
			return;
		}

		PlayerSort sort = new PlayerSort();
		if (sort.hasNotFound())
			sender.sendMessage(Main.getPrefix() + Main.getColorCode()
					+ "Es konnte nicht für alle Spieler ein Loch gefunden werden!");
		sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "Alle Spieler §7wurden sortiert!");
		return;
	}
}
