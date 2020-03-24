package de.cuuky.varo.command.varo;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.gui.admin.config.ConfigSectionGUI;
import de.cuuky.varo.utils.JavaUtils;

public class ConfigCommand extends VaroCommand {

	public ConfigCommand() {
		super("config", "Hauptbefehl fuer die Config", "varo.config", "configuration");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer player, Command cmd, String label, String[] args) {
		if(args.length == 0) {
			sender.sendMessage(Main.getPrefix() + "§7----- " + Main.getColorCode() + "Config §7-----");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/config set §7<key> <value>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/config search <Keyword>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/config menu");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/config reload");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/config reset");
			sender.sendMessage(Main.getPrefix() + "§7----------------------");
			return;
		}

		if(args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("refresh")) {
			Main.getDataManager().reloadConfig();
			sender.sendMessage(Main.getPrefix() + "§7Erfolgreich " + Main.getColorCode() + "alle Listen§7, die " + Main.getColorCode() + "Messages §7und die " + Main.getColorCode() + "Config §7neu geladen!");
		} else if(args[0].equalsIgnoreCase("set")) {
			if(args.length != 3) {
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/config §7set <key> <value>");
				return;
			}

			for(ConfigSetting entry : ConfigSetting.values()) {
				if(!entry.getPath().equalsIgnoreCase(args[1]))
					continue;

				Object arg = JavaUtils.getStringObject(args[2]);
				entry.setValue(arg, true);

				sender.sendMessage(Main.getPrefix() + "§7Erfolgreich den Eintrag '§a" + entry.getPath() + "§7' auf '§a" + entry.getValue() + "§7' gesetzt!");
				return;
			}

			sender.sendMessage(Main.getPrefix() + "§7Den Eintrag " + Main.getColorCode() + args[1] + "§7 gibt es nicht in der Config!");
		} else if(args[0].equalsIgnoreCase("reset")) {
			for(ConfigSetting entry : ConfigSetting.values())
				entry.setValue(entry.getDefaultValue(), true);

			sender.sendMessage(Main.getPrefix() + "§7Erfolgreich alle Eintraege zurueckgesetzt!");
		}else if(args[0].equalsIgnoreCase("menu")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage(Main.getPrefix() + "Nicht fuer die Konsole!");
				return;
			}
			
			new ConfigSectionGUI((Player) sender);
		} else if(args[0].equalsIgnoreCase("search")) {
			if(args.length != 2) {
				sender.sendMessage(Main.getPrefix() + "/config search <Keyword>");
				return;
			}
			
			String needle = args[1];
			ArrayList<ConfigSetting> foundSettings = new ArrayList<>();
			
			for(ConfigSetting setting : ConfigSetting.values()) {
				if(!setting.getFullPath().toLowerCase().contains(needle))
					continue;
				
				foundSettings.add(setting);
			}
			
			if(foundSettings.isEmpty()) {
				sender.sendMessage(Main.getPrefix() + "Fuer " + Main.getColorCode() + needle + " §7konnte kein ConfigEintrag gefunden werden!");
				return;
			}
			
			sender.sendMessage(Main.getPrefix() + "§lFolgende Einstellungen wurden gefunden:");
			for(ConfigSetting setting : foundSettings) 
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + setting.getPath() + " §8- §7" + JavaUtils.getArgsToString(setting.getDescription(), " "));
		} else
			sender.sendMessage(Main.getPrefix() + "§7Command '" + args[0] + "' not found! §7Type /config for help.");
	}
}