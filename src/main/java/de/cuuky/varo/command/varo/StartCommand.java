package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.game.VaroGame;
import de.cuuky.varo.player.VaroPlayer;

public class StartCommand extends VaroCommand {

	public StartCommand() {
		super("start", "Startet das Varo", "varo.start");
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
}