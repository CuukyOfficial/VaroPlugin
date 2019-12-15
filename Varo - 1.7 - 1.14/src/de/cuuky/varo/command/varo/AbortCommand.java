package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.game.Game;

public class AbortCommand extends VaroCommand {

	public AbortCommand() {
		super("abort", "Bricht den Startcountdown ab", "varo.abort", "abbruch", "abbrechen", "stop");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if(!Game.getInstance().isStarting())
			return;

		Game.getInstance().abort();
	}
}