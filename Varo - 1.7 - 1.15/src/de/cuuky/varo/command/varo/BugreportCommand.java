package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.recovery.recoveries.VaroBugreport;
import de.cuuky.varo.spigot.updater.VaroUpdateResultSet.UpdateResult;

public class BugreportCommand extends VaroCommand {

	public BugreportCommand() {
		super("bugreport", "Hift bei der Fehlersuche und beim Reporten von Bugs", "varo.bug", "bug", "bughelp", "error");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if(Main.getVaroUpdater().getLastResult().getUpdateResult() == UpdateResult.UPDATE_AVAILABLE) {
			sender.sendMessage(Main.getPrefix() + "Du kannst keine Bugreports von einer alten Plugin-Version machen!");
			sender.sendMessage(Main.getPrefix() + "Derzeitige Version: §c" + Main.getInstance().getDescription().getVersion());
			sender.sendMessage(Main.getPrefix() + "Neueste Version: §a" + Main.getVaroUpdater().getLastResult().getVersionName());
			sender.sendMessage(Main.getPrefix() + "§a/varo update");
			return;
		}
		
		VaroBugreport bugreport = new VaroBugreport();
		if(bugreport.hasFailed()) {
			sender.sendMessage(Main.getPrefix() + "Ein Fehler ist aufgetreten!");
			return;
		}
		
		sender.sendMessage(Main.getPrefix() + "Bugreport wurde unter §c" + bugreport.getZipFile().getPath() + " §7gespeichert!");
		sender.sendMessage(Main.getPrefix() + "Bitte sende diesen auf den Discord in den Support!");
	}
}