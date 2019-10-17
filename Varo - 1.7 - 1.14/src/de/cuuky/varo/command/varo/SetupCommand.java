package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.gui.admin.setuphelp.SetupHelpGUI;
import de.cuuky.varo.player.VaroPlayer;

public class SetupCommand extends VaroCommand {

	public SetupCommand() {
		super("setup", "Öffnet die Setuphilfe", "varo.setup");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if (vp == null) {
			sender.sendMessage(Main.getPrefix() + "Du musst ein Spieler sein!");
			return;
		}

		new SetupHelpGUI(vp.getPlayer());
	}
}
