package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.recovery.recoveries.VaroBugreport;

public class BugreportCommand extends VaroCommand {

	public BugreportCommand() {
		super("bugreport", "Hift bei der Fehlersuche und beim Reporten von Bugs", "varo.bug", "bug", "bughelp", "error");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		VaroBugreport bugreport = new VaroBugreport();
		
		sender.sendMessage(Main.getPrefix() + "Bugreport wurde unter §c" + bugreport.getZipFile().getName() + " §7gespeichert!");
		sender.sendMessage(Main.getPrefix() + "Bitte sende diesen auf den Discord in den Support!");
	}
}