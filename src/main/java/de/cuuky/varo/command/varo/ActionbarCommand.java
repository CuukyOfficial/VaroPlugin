package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.config.language.Messages;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.player.VaroPlayer;

public class ActionbarCommand extends VaroCommand {

	public ActionbarCommand() {
		super("actionbar", "Aktiviert/Deaktiviert die Actionbar", "varo.actionbar", "ab");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if (vp == null) {
			Messages.COMMANDS_ERROR_NO_CONSOLE.send(vp);
			return;
		}
		
		if (!ConfigSetting.ACTIONBAR_ENABLED.getValueAsBoolean() || vp.getActionbar() == null) {
			Messages.COMMANDS_VARO_ACTIONBAR_DEACTIVATED.send(vp);
			return;
		}

		if (vp.getStats().isShowActionbar()) {
			vp.getStats().setShowActionbar(false);
			vp.getActionbar().setEnabled(false);
			Messages.COMMANDS_VARO_ACTIONBAR_DISABLED.send(vp);
		} else {
			vp.getStats().setShowActionbar(true);
			vp.getActionbar().setEnabled(true);
			Messages.COMMANDS_VARO_ACTIONBAR_ENABLED.send(vp);
		}
	}
}