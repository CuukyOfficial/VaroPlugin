package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.config.language.Messages;
import de.cuuky.varo.gui.admin.SetupHelpGUI;
import de.cuuky.varo.player.VaroPlayer;

public class SetupCommand extends VaroCommand {

	public SetupCommand() {
		super("setup", "Öffnet die Setuphilfe", "varo.setup");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if (vp == null) {
			Messages.COMMANDS_ERROR_NO_CONSOLE.send(vp);
			return;
		}

		new SetupHelpGUI(vp.getPlayer());
	}
}