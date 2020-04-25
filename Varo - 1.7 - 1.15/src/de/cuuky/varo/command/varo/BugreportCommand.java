package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.cfw.version.BukkitVersion;
import de.cuuky.cfw.version.VersionUtils;
import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.recovery.VaroFileUploader;
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
		VaroFileUploader uploader = new VaroFileUploader(bugreport.getZipFile());

		sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_BUGREPORT_UPLOADING.getValue(vp));
		String url = uploader.uploadFile();

		if (url == null) {
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_BUGREPORT_UPLOAD_ERROR.getValue(vp));
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_BUGREPORT_SEND_TO_DISCORD.getValue(vp));
		} else {
			String message = Main.getPrefix() + ConfigMessages.VARO_COMMANDS_BUGREPORT_UPLOADED.getValue(vp).replace("%url%", url);
			if (vp != null && VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_7))
				vp.getNetworkManager().sendLinkedMessage(message + ConfigMessages.VARO_COMMANDS_BUGREPORT_CLICK_ME.getValue(vp), url);
			else
				sender.sendMessage(message);
		}
	}
}