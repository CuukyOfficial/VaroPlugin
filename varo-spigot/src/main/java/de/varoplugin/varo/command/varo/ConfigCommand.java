package de.varoplugin.varo.command.varo;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.varoplugin.varo.Main;
import de.varoplugin.varo.command.VaroCommand;
import de.varoplugin.varo.config.language.Messages;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;
import de.varoplugin.varo.gui.admin.config.ConfigSectionGUI;
import de.varoplugin.varo.player.VaroPlayer;
import io.github.almightysatan.slams.Placeholder;
import io.github.almightysatan.slams.PlaceholderResolver;

public class ConfigCommand extends VaroCommand {

	public ConfigCommand() {
		super("config", "Hauptbefehl fuer die Config", "varo.config", "configuration");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if (args.length == 0) {
		    Messages.CATEGORY_HEADER.send(sender, Placeholder.constant("category", "Config"));
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " config set §7<key> <value>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " config search <Keyword>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " config menu");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " config reload");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " config reset");
			Messages.CATEGORY_FOOTER.send(sender, Placeholder.constant("category", "Config"));
			return;
		}

		if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("refresh")) {
			Main.getDataManager().reloadConfig();
			Messages.COMMANDS_VARO_CONFIG_RELOADED.send(sender);
		} else if (args[0].equalsIgnoreCase("set")) {
			if (args.length != 3) {
				Messages.COMMANDS_VARO_CONFIG_HELP_SET.send(sender);
				return;
			}

			for (ConfigSetting entry : ConfigSetting.values()) {
				if (!entry.getFullPath().equalsIgnoreCase(args[1]))
					continue;

				if (!entry.canParseFromString() || entry.isSensitive()) {
				    Messages.COMMANDS_VARO_CONFIG_NO_INGAME_SET.send(sender);
					return;
				}
				
				try {
					entry.setStringValue(args[2], true);
				} catch(Throwable t) {
					Messages.COMMANDS_VARO_CONFIG_ERROR_SET.send(sender, Placeholder.constant("error", t.getClass() + " " + t.getMessage())); // TODO improve error handling
					return;
				}

				Messages.COMMANDS_VARO_CONFIG_ENTRY_SET.send(sender, PlaceholderResolver.builder().constant("entry", entry.getFullPath()).constant("value", args[2]).build());
				return;
			}

			Messages.COMMANDS_VARO_CONFIG_ENTRY_NOT_FOUND.send(sender, Placeholder.constant("entry", args[1]));
		} else if (args[0].equalsIgnoreCase("reset")) {
			for (ConfigSetting entry : ConfigSetting.values())
				entry.setValue(entry.getDefaultValue(), true);

			Messages.COMMANDS_VARO_CONFIG_RESET.send(sender);
		} else if (args[0].equalsIgnoreCase("menu")) {
			if (!(sender instanceof Player)) {
			    Messages.COMMANDS_ERROR_NO_CONSOLE.send(sender);
				return;
			}

			new ConfigSectionGUI((Player) sender);
		} else if (args[0].equalsIgnoreCase("search")) {
			if (args.length != 2) {
			    Messages.COMMANDS_VARO_CONFIG_HELP_SEARCH.send(sender);
				return;
			}

			String needle = args[1].toLowerCase();
			ArrayList<ConfigSetting> foundSettings = new ArrayList<>();

			for (ConfigSetting setting : ConfigSetting.values()) {
				if (!setting.getFullPath().toLowerCase().contains(needle))
					continue;

				foundSettings.add(setting);
			}

			if (foundSettings.isEmpty()) {
				Messages.COMMANDS_VARO_CONFIG_ENTRY_NOT_FOUND.send(sender, Placeholder.constant("entry", args[1]));
				return;
			}

			Messages.COMMANDS_VARO_CONFIG_SEARCH_LIST_TITLE.send(sender);
			for (ConfigSetting setting : foundSettings)
			    Messages.COMMANDS_VARO_CONFIG_SEARCH_LIST_FORMAT.send(sender, PlaceholderResolver.builder().constant("entry", setting.getFullPath()).constant("description", String.join(" ", setting.getDescription())).build());
		} else
		    Messages.COMMANDS_ERROR_USAGE.send(sender, Placeholder.constant("command", "config"));
	}
}