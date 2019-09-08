package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.player.VaroPlayer;

public class TrollCommand extends VaroCommand {

	public TrollCommand() {
		super("troll", "Um Spieler zu trollen!", "varo.troll", "annoy");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if(vp == null) {
			sender.sendMessage(Main.getPrefix() + "Du musst ein Spieler sein!");
			return;
		}

		sender.sendMessage(Main.getPrefix() + "Nearly finished - will be added soon");
	}
}
