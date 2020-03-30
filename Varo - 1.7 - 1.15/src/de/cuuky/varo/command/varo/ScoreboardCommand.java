package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.scoreboard.DisplaySlot;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.messages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;

public class ScoreboardCommand extends VaroCommand {

	public ScoreboardCommand() {
		super("scoreboard", "Aktiviert/Deaktiviert das Scoreboard", "varo.scoreboard", "sb");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if(vp == null) {
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_ERROR_NO_CONSOLE.getValue());
			return;
		}

		if(!ConfigSetting.SCOREBOARD.getValueAsBoolean()) {
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_SCOREBOARD_DEACTIVATED.getValue());
			return;
		}

		if(vp.getStats().isShowScoreboard()) {
			vp.getPlayer().getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
			vp.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_SCOREBOARD_DISABLED.getValue());
			vp.getStats().setShowScoreboard(false);
		} else {
			vp.getStats().setShowScoreboard(true);
			Main.getDataManager().getScoreboardHandler().sendScoreBoard(vp);
			vp.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_SCOREBOARD_ENABLED.getValue());
			if(vp.getNametag() != null)
				vp.getNametag().giveAll();
		}

		vp.update();
	}
}