package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.gui.admin.varoevents.VaroEventGUI;

public class EventsCommand extends VaroCommand {

	public EventsCommand() {
		super("events", "Öffnet das Event Menue", "varo.events", "event", "scenario", "scenarios");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if (vp == null) {
			sender.sendMessage(Main.getPrefix() + "Du musst ein Spieler sein!");
			return;
		}

		new VaroEventGUI(vp.getPlayer());
	}
}