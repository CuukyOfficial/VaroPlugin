package de.cuuky.varo.command.varo;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.messages.ConfigMessages;
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
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_HELP_HEADER.getValue().replace("%category%", "Config"));
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/config set §7<key> <value>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/config search <Keyword>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/config menu");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/config reload");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/config reset");
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_HELP_FOOTER.getValue());
			return;
		}

		if(args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("refresh")) {
			Main.getDataManager().reloadConfig();
			Main.getDataManager().reloadPlayerClients();
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_CONFIG_RELOADED.getValue());
		} else if(args[0].equalsIgnoreCase("set")) {
			if(args.length != 3) {
				sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_CONFIG_HELP_SET.getValue());
				return;
			}

			for(ConfigSetting entry : ConfigSetting.values()) {
				if(!entry.getPath().equalsIgnoreCase(args[1]))
					continue;

				Object arg = JavaUtils.getStringObject(args[2]);
				entry.setValue(arg, true);

				sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_CONFIG_ENTRY_SET.getValue());
				return;
			}

			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_CONFIG_ENTRY_NOT_FOUND.getValue().replace("%entry%", args[1]));
		} else if(args[0].equalsIgnoreCase("reset")) {
			for(ConfigSetting entry : ConfigSetting.values())
				entry.setValue(entry.getDefaultValue(), true);

			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_CONFIG_RESET.getValue());
		}else if(args[0].equalsIgnoreCase("menu")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_ERROR_NO_CONSOLE.getValue());
				return;
			}
			
			new ConfigSectionGUI((Player) sender);
		} else if(args[0].equalsIgnoreCase("search")) {
			if(args.length != 2) {
				sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_CONFIG_HELP_SEARCH.getValue());
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
				sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_CONFIG_ENTRY_NOT_FOUND.getValue().replace("%entry%", needle));
				return;
			}
			
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_CONFIG_SEARCH_LIST_TITLE.getValue());
			for(ConfigSetting setting : foundSettings) 
				sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_CONFIG_SEARCH_LIST_FORMAT.getValue().replace("%entry%", setting.getFullPath().toString()).replace("%description%", JavaUtils.getArgsToString(setting.getDescription(), " ")));
		} else
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_ERROR_USAGE.getValue().replace("%command%", "config"));
	}
}