package de.cuuky.varo.command.varo;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.app.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.game.lobby.LobbyItem;

public class AbortCommand extends VaroCommand {

	public AbortCommand() {
		super("abort", "Bricht den Startcountdown ab", "varo.abort", "abbruch", "abbrechen", "stop");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if (!Main.getVaroGame().isStarting()) {
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_ABORT_COUNTDOWN_NOT_ACTIVE.getValue(vp));
			return;
		}

		Main.getVaroGame().abort();
		Bukkit.getOnlinePlayers().forEach(player -> LobbyItem.giveItems(player));
		sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_ABORT_COUNTDOWN_STOPPED.getValue(vp));
	}
}