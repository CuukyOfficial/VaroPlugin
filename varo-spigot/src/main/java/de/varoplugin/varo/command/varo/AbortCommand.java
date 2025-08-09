package de.varoplugin.varo.command.varo;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.varoplugin.varo.Main;
import de.varoplugin.varo.command.VaroCommand;
import de.varoplugin.varo.config.language.Messages;
import de.varoplugin.varo.game.LobbyItem;
import de.varoplugin.varo.player.VaroPlayer;

public class AbortCommand extends VaroCommand {

	public AbortCommand() {
		super("abort", "Bricht den Startcountdown ab", "varo.abort", "abbruch", "abbrechen", "stop");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if (!Main.getVaroGame().isStarting()) {
			Messages.COMMANDS_VARO_ABORT_COUNTDOWN_NOT_ACTIVE.send(sender);
			return;
		}

		Main.getVaroGame().abort();
		Bukkit.getOnlinePlayers().forEach(player -> LobbyItem.giveItems(player));
		Messages.COMMANDS_VARO_ABORT_COUNTDOWN_STOPPED.send(sender);
	}
}