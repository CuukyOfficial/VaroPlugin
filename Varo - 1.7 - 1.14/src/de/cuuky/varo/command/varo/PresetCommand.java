package de.cuuky.varo.command.varo;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.data.DataManager;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.preset.PresetLoader;

public class PresetCommand extends VaroCommand {

	public PresetCommand() {
		super("preset", "Command für die Presets", "varo.preset", "presettings", "presets");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if(args.length == 0) {
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo preset §7load <PresetPath>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo preset §7save <PresetPath>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo preset §7list");
			return;
		}

		if(args[0].equalsIgnoreCase("load")) {
			if(args.length != 2) {
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo preset §7load <PresetPath>");
				return;
			}

			PresetLoader loader = new PresetLoader(args[1]);
			if(!loader.getFile().isDirectory()) {
				sender.sendMessage(Main.getPrefix() + args[1] + " nicht gefunden!");
				return;
			}

			loader.loadSettings();
			DataManager.getInstance().reloadConfig();
			sender.sendMessage(Main.getPrefix() + "Einstellungen '" + args[1] + "' erfolgreich geladen!");
		} else if(args[0].equalsIgnoreCase("save")) {
			if(args.length != 2) {
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo preset §7save <PresetPath>");
				return;
			}

			PresetLoader loader = new PresetLoader(args[1]);
			loader.copyCurrentSettingsTo();
			sender.sendMessage(Main.getPrefix() + "Derzeitige Einstellungen erfolgreich unter '" + args[1] + "' gespeichert!");
		} else if(args[0].equalsIgnoreCase("list")) {
			File file = new File("plugins/Varo/presets");
			sender.sendMessage(Main.getPrefix() + "§lListe aller Presets:");
			for(File f : file.listFiles())
				sender.sendMessage(Main.getPrefix() + f.getName());
		} else
			sender.sendMessage(Main.getPrefix() + "Command not found! /varo preset");

	}
}