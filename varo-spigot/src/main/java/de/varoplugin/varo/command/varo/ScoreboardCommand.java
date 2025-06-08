package de.varoplugin.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.varoplugin.varo.command.VaroCommand;
import de.varoplugin.varo.config.language.Messages;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;
import de.varoplugin.varo.player.VaroPlayer;

public class ScoreboardCommand extends VaroCommand {

	public ScoreboardCommand() {
		super("scoreboard", "Aktiviert/Deaktiviert das Scoreboard", "varo.scoreboard", "sb");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if (vp == null) {
			Messages.COMMANDS_ERROR_NO_CONSOLE.send(sender);
			return;
		}

		if (!ConfigSetting.SCOREBOARD_ENABLED.getValueAsBoolean() || vp.getScoreboard() == null) {
			Messages.COMMANDS_VARO_SCOREBOARD_DEACTIVATED.send(sender);
			return;
		}

		if (vp.getStats().isShowScoreboard()) {
			Messages.COMMANDS_VARO_SCOREBOARD_DISABLED.send(sender);
			vp.getStats().setShowScoreboard(false);
			vp.getScoreboard().setEnabled(false);
		} else {
			vp.getStats().setShowScoreboard(true);
			vp.getScoreboard().setEnabled(true);
			Messages.COMMANDS_VARO_SCOREBOARD_ENABLED.send(sender);
		}

		if (vp.isOnline())
			vp.update();
	}
}