package de.varoplugin.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.varoplugin.varo.command.VaroCommand;
import de.varoplugin.varo.config.language.Messages;
import de.varoplugin.varo.gui.admin.VaroEventGUI;
import de.varoplugin.varo.player.VaroPlayer;

public class EventsCommand extends VaroCommand {

	public EventsCommand() {
		super("events", "Öffnet das Event Menue", "varo.events", "event", "scenario", "scenarios");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if (vp == null) {
			Messages.COMMANDS_ERROR_NO_CONSOLE.send(sender);
			return;
		}

		new VaroEventGUI(vp.getPlayer());
	}
}