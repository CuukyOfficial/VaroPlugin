package de.cuuky.varo.command.varo;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.preset.PresetLoader;

public class PresetCommand extends VaroCommand {

	public PresetCommand() {
		super("preset", "Command fuer die Presets", "varo.preset", "presettings", "presets");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_HELP_HEADER.getValue(vp).replace("%category%", "Presets"));
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " preset §7load <PresetPath>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " preset §7save <PresetPath>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " preset §7list");
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_HELP_FOOTER.getValue(vp));
			return;
		}

		if (args[0].equalsIgnoreCase("load")) {
			if (args.length != 2) {
				sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_PRESET_HELP_LOAD.getValue(vp));
				return;
			}

			PresetLoader loader = new PresetLoader(args[1]);
			if (!loader.getFile().isDirectory()) {
				sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_PRESET_NOT_FOUND.getValue(vp).replace("%preset%", args[1]));
				return;
			}

			if (loader.loadSettings()) {
				Main.getDataManager().reloadConfig();
				sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_PRESET_LOADED.getValue(vp).replace("%preset%", args[1]));
			} else
				sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_PRESET_PATH_TRAVERSAL.getValue(vp).replace("%preset%", args[1]));
		} else if (args[0].equalsIgnoreCase("save")) {
			if (args.length != 2) {
				sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_PRESET_HELP_SAVE.getValue(vp));
				return;
			}

			PresetLoader loader = new PresetLoader(args[1]);
			if (loader.copyCurrentSettingsTo())
				sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_PRESET_SAVED.getValue(vp).replace("%preset%", args[1]));
			else
				sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_PRESET_PATH_TRAVERSAL.getValue(vp).replace("%preset%", args[1]));
		} else if (args[0].equalsIgnoreCase("list")) {
			File file = new File("plugins/Varo/presets");
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_PRESET_LIST.getValue(vp));
			for (File f : file.listFiles())
				sender.sendMessage(Main.getPrefix() + f.getName());
		} else
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_ERROR_USAGE.getValue(vp).replace("%command%", "preset"));

	}
}