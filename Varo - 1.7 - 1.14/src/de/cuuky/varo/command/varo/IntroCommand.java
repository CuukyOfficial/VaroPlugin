package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.game.suro.SuroStart;

public class IntroCommand extends VaroCommand {

	private SuroStart suroStart;

	public IntroCommand() {
		super("intro", "Startet das SURO Intro", "varo.intro");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if(suroStart != null) {
			sender.sendMessage(Main.getPrefix() + "Intro läuft bereits!");
			return;
		}

		if(Main.getGame().hasStarted()) {
			sender.sendMessage(Main.getPrefix() + "Das Spiel wurde bereits gestartet!");
			return;
		}

		suroStart = new SuroStart();
		sender.sendMessage(Main.getPrefix() + "Und los geht's!");
	}
}