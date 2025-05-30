package de.cuuky.varo.command.varo;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.config.language.Messages;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.preset.PresetLoader;
import io.github.almightysatan.slams.Placeholder;

public class PresetCommand extends VaroCommand {

	public PresetCommand() {
		super("preset", "Command fuer die Presets", "varo.preset", "presettings", "presets");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if (args.length == 0) {
		    Messages.CATEGORY_HEADER.send(sender, Placeholder.constant("category", "Presets"));
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " preset §7load <PresetPath>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " preset §7save <PresetPath>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " preset §7list");
			Messages.CATEGORY_FOOTER.send(sender, Placeholder.constant("category", "Presets"));
			return;
		}

		if (args[0].equalsIgnoreCase("load")) {
			if (args.length != 2) {
				Messages.COMMANDS_VARO_PRESET_HELP_LOAD.send(sender);
				return;
			}

			PresetLoader loader = new PresetLoader(args[1]);
			if (!loader.getFile().isDirectory()) {
				Messages.COMMANDS_VARO_PRESET_NOT_FOUND.send(sender, Placeholder.constant("preset", args[1]));
				return;
			}

			if (loader.loadSettings()) {
				Main.getDataManager().reloadConfig();
				Messages.COMMANDS_VARO_PRESET_LOADED.send(sender, Placeholder.constant("preset", args[1]));
			} else
			    Messages.COMMANDS_VARO_PRESET_PATH_TRAVERSAL.send(sender, Placeholder.constant("preset", args[1]));
		} else if (args[0].equalsIgnoreCase("save")) {
			if (args.length != 2) {
			    Messages.COMMANDS_VARO_PRESET_HELP_SAVE.send(sender);
				return;
			}

			PresetLoader loader = new PresetLoader(args[1]);
			if (loader.copyCurrentSettingsTo())
			    Messages.COMMANDS_VARO_PRESET_SAVED.send(sender, Placeholder.constant("preset", args[1]));
			else
			    Messages.COMMANDS_VARO_PRESET_PATH_TRAVERSAL.send(sender, Placeholder.constant("preset", args[1]));
		} else if (args[0].equalsIgnoreCase("list")) {
			File file = new File("plugins/Varo/presets");
			Messages.COMMANDS_VARO_PRESET_LIST.send(sender);
			for (File f : file.listFiles())
				sender.sendMessage(Main.getPrefix() + f.getName());
		} else
		    Messages.COMMANDS_ERROR_USAGE.send(sender, Placeholder.constant("command", "preset"));

	}
}