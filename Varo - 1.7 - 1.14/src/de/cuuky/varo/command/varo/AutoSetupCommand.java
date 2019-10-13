package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.world.AutoSetup;

public class AutoSetupCommand extends VaroCommand {

	public AutoSetupCommand() {
		super("autosetup", "Setzt den Server automatisch auf", "varo.autosetup");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if(args.length == 0) {
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo autostart §7<true/false>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo autostart §7run");
			sender.sendMessage(Main.getPrefix() + "§cVorsicht: §7Dieser Command setzt fest, dass beim nächsten Start des Servers der AutoStart genutzt wird.");
			return;
		}
		
		if (args[0].equalsIgnoreCase("run")) {
			if (!ConfigEntry.AUTOSETUP_ENABLED.getValueAsBoolean()) {
				sender.sendMessage(Main.getPrefix() + "Der AutoStart wurde noch nicht in der Config eingerichtet!");
				return;
			}
			
			new AutoSetup();
			sender.sendMessage(Main.getPrefix() + "Der AutoStart ist fertig, joine nun neu.");
			return;
		}

		boolean toggle = false;
		try {
			toggle = Boolean.valueOf(args[0]);
		} catch(ClassCastException e) {
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo autostart §7<true/false>");
		}

		Main.getGame().setWillSetupNext(toggle);
		if(toggle)
			sender.sendMessage(Main.getPrefix() + "AutoSetup wird beim nächsten Start ausgeführt!");
		else
			sender.sendMessage(Main.getPrefix() + "AutoSetup wird beim nächsten Start nicht ausgeführt!");
	}
}
