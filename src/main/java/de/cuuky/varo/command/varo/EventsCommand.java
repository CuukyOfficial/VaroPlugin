package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.config.language.Messages;
import de.cuuky.varo.gui.admin.VaroEventGUI;
import de.cuuky.varo.player.VaroPlayer;

public class EventsCommand extends VaroCommand {

	public EventsCommand() {
		super("events", "Öffnet das Event Menue", "varo.events", "event", "scenario", "scenarios");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if (vp == null) {
			Messages.COMMANDS_ERROR_NO_CONSOLE.send(vp);
			return;
		}

		new VaroEventGUI(vp.getPlayer());
	}
}