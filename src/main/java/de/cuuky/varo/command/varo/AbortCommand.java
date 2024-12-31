package de.cuuky.varo.command.varo;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.config.language.Messages;
import de.cuuky.varo.game.LobbyItem;
import de.cuuky.varo.player.VaroPlayer;

public class AbortCommand extends VaroCommand {

	public AbortCommand() {
		super("abort", "Bricht den Startcountdown ab", "varo.abort", "abbruch", "abbrechen", "stop");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if (!Main.getVaroGame().isStarting()) {
			Messages.COMMANDS_VARO_ABORT_COUNTDOWN_NOT_ACTIVE.send(vp);
			return;
		}

		Main.getVaroGame().abort();
		Bukkit.getOnlinePlayers().forEach(player -> LobbyItem.giveItems(player));
		Messages.COMMANDS_VARO_ABORT_COUNTDOWN_STOPPED.send(vp);
	}
}