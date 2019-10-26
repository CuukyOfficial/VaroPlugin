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
		if(args.length >= 1) {
			if(args[0].equalsIgnoreCase("run")) {
				if(!ConfigEntry.AUTOSETUP_ENABLED.getValueAsBoolean()) {
					sender.sendMessage(Main.getPrefix() + "Der AutoSetup wurde noch nicht in der Config eingerichtet!");
					return;
				}

				new AutoSetup();
				sender.sendMessage(Main.getPrefix() + "Der AutoSetup ist fertig. Joine nun neu, um zu den Spawns zu kommen.");
				return;
			}
		}

		sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo autosetup run §7startet den Autosetup");
		sender.sendMessage(Main.getPrefix() + "§cVorsicht: §7Dieser Befehl setzt neue Spawns, Lobby, Portal, Border und §loptional§7 einen Autostart.");
	}
}
