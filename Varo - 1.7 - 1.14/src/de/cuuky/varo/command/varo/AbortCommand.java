package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.entity.player.VaroPlayer;

public class AbortCommand extends VaroCommand {

	public AbortCommand() {
		super("abort", "Bricht den Startcountdown ab", "varo.abort");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if(!Main.getGame().isStarting())
			return;

		Main.getGame().abort();
	}
}
