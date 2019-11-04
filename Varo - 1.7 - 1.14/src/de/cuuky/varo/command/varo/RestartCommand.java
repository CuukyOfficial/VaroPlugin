package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.game.state.GameState;

public class RestartCommand extends VaroCommand {

	public RestartCommand() {
		super("restart", "Restartet das Projekt", "varo.restart");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if(Main.getGame().getGameState() == GameState.LOBBY) {
			sender.sendMessage(Main.getPrefix() + "Varo wurde bereits neu gestartet!");
			return;
		}

		Main.getGame().setGamestate(GameState.LOBBY);
		sender.sendMessage(Main.getPrefix() + "Varo wurde neu gestartet!");
	}

}
