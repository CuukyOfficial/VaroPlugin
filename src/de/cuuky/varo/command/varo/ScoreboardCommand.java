package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardCommand extends VaroCommand {
	public ScoreboardCommand() {
		super("scoreboard", "Aktiviert/Deaktiviert das Scoreboard", "varo.scoreboard", null, "sb");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if (vp == null) {
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_ERROR_NO_CONSOLE.getValue(vp));
			return;
		}

		if (!ConfigSetting.SCOREBOARD.getValueAsBoolean() || vp.getScoreboard() == null) {
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_SCOREBOARD_DEACTIVATED.getValue(vp));
			return;
		}

		if (vp.getStats().isShowScoreboard()) {
			vp.sendMessage(ConfigMessages.VARO_COMMANDS_SCOREBOARD_DISABLED);
			vp.getStats().setShowScoreboard(false);
			vp.getScoreboard().setEnabled(false);
		} else {
			vp.getStats().setShowScoreboard(true);
			vp.getScoreboard().setEnabled(true);
			vp.sendMessage(ConfigMessages.VARO_COMMANDS_SCOREBOARD_ENABLED);
		}

		if (vp.isOnline())
			vp.update();
	}
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		return new ArrayList<>();
	}
}