package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.recovery.recoveries.VaroBugreport;
import de.cuuky.varo.spigot.updater.VaroUpdateResultSet.UpdateResult;

public class BugreportCommand extends VaroCommand {

	public BugreportCommand() {
		super("bugreport", "Hift bei der Fehlersuche und beim Reporten von Bugs", "varo.bug", "bug", "bughelp", "error", "support");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if (Main.getVaroUpdater().getLastResult().getUpdateResult() == UpdateResult.UPDATE_AVAILABLE) {
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_BUGREPORT_OUTDATED_VERSION.getValue(vp));
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_BUGREPORT_CURRENT_VERSION.getValue(vp).replace("%version%", Main.getInstance().getDescription().getVersion().toString()));
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_BUGREPORT_NEWEST_VERSION.getValue(vp).replace("%version%", Main.getVaroUpdater().getLastResult().getVersionName()));
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_BUGREPORT_UPDATE.getValue(vp));
			return;
		}

		sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_BUGREPORT_COLLECTING_DATA.getValue(vp));
		VaroBugreport bugreport = new VaroBugreport();
		if (bugreport.hasFailed()) {
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_ERROR.getValue(vp));
			return;
		}

		sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_BUGREPORT_CREATED.getValue(vp).replace("%filename%", bugreport.getZipFile().getPath().toString()));
	}
}