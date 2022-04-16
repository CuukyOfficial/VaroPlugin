package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.app.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;

public class ActionbarCommand extends VaroCommand {

	public ActionbarCommand() {
		super("actionbar", "Aktiviert/Deaktiviert die Actionbar-Zeit", "varo.actionbar", "ab");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if (vp == null) {
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_ERROR_NO_CONSOLE.getValue(vp));
			return;
		}

		if (vp.getStats().isShowActionbarTime()) {
			vp.getStats().setShowActionbarTime(false);
			vp.sendMessage(ConfigMessages.VARO_COMMANDS_ACTIONBAR_DEACTIVATED);
		} else {
			vp.getStats().setShowActionbarTime(true);
			vp.sendMessage(ConfigMessages.VARO_COMMANDS_ACTIONBAR_ACTIVATED);
		}
	}
}