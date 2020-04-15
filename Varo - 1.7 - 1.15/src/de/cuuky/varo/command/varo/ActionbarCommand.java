package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.configuration.configurations.messages.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;

public class ActionbarCommand extends VaroCommand {

	public ActionbarCommand() {
		super("actionbar", "Aktiviert/Deaktiviert die Actionbar-Zeit", "varo.actionbar", "ab");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if(vp == null) {
			sender.sendMessage(Main.getPrefix() + "Du musst ein Spieler sein!");
			return;
		}

		if(vp.getStats().isShowActionbarTime()) {
			vp.getStats().setShowActionbarTime(false);
			vp.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_ACTIONBAR_DEACTIVATED.getValue(vp));
		} else {
			vp.getStats().setShowActionbarTime(true);
			vp.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_ACTIONBAR_ACTIVATED.getValue(vp));
		}
	}
}