package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.player.VaroPlayer;

public class ActionbarCommand extends VaroCommand {

	public ActionbarCommand() {
		super("actionbar", "Aktiviert/Deaktiviert die Actionbar", "varo.actionbar", "ab");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if (vp == null) {
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_ERROR_NO_CONSOLE.getValue(vp));
			return;
		}
		
		if (!ConfigSetting.ACTIONBAR.getValueAsBoolean() || vp.getActionbar() == null) {
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_ACTIONBAR_DEACTIVATED.getValue(vp));
			return;
		}

		if (vp.getStats().isShowActionbar()) {
			vp.getStats().setShowActionbar(false);
			vp.getActionbar().setEnabled(false);
			vp.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_ACTIONBAR_DISABLED.getValue(vp));
		} else {
			vp.getStats().setShowActionbar(true);
			vp.getActionbar().setEnabled(true);
			vp.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_ACTIONBAR_ENABLED.getValue(vp));
		}
	}
}