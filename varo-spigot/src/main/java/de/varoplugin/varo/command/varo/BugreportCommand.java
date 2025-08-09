package de.varoplugin.varo.command.varo;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.varoplugin.varo.Main;
import de.varoplugin.varo.command.VaroCommand;
import de.varoplugin.varo.config.language.Messages;
import de.varoplugin.varo.data.BugReport;
import de.varoplugin.varo.player.VaroPlayer;
import de.varoplugin.varo.spigot.VaroUpdateResultSet.UpdateResult;
import io.github.almightysatan.slams.Placeholder;

public class BugreportCommand extends VaroCommand {

	public BugreportCommand() {
		super("bugreport", "Hift bei der Fehlersuche und beim Reporten von Bugs", "varo.bug", "bug", "bughelp", "error", "support");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if (Main.getVaroUpdater() != null && Main.getVaroUpdater().getLastResult() != null
		        &&  Main.getVaroUpdater().getLastResult().getUpdateResult() == UpdateResult.UPDATE_AVAILABLE) {
			Messages.COMMANDS_VARO_BUGREPORT_UPDATE.send(sender);
			return;
		}

		Messages.COMMANDS_VARO_BUGREPORT_COLLECTING_DATA.send(sender);
		File bugReport = BugReport.createBugReport();
		if (bugReport == null) {
			Messages.COMMANDS_ERROR_GENERIC.send(sender);
			return;
		}

		Messages.COMMANDS_VARO_BUGREPORT_CREATED.send(sender, Placeholder.constant("file", bugReport.getAbsolutePath()));
	}
}