package de.varoplugin.varo.command.varo;

import de.varoplugin.varo.Main;
import de.varoplugin.varo.command.VaroCommand;
import de.varoplugin.varo.config.language.Messages;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;
import de.varoplugin.varo.player.VaroPlayer;
import de.varoplugin.varo.preset.Preset;
import io.github.almightysatan.slams.Placeholder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.io.IOException;
import java.util.Optional;

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
            
            Optional<Preset> preset = Preset.listPresets().stream().filter(p -> p.getName().equals(args[1])).findAny();
			if (!preset.isPresent()) {
				Messages.COMMANDS_VARO_PRESET_NOT_FOUND.send(sender, Placeholder.constant("preset", args[1]));
				return;
			}

            try {
                preset.get().load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Messages.COMMANDS_VARO_PRESET_LOADED.send(sender, Placeholder.constant("preset", args[1]));
		} else if (args[0].equalsIgnoreCase("save")) {
			if (args.length != 2) {
			    Messages.COMMANDS_VARO_PRESET_HELP_SAVE.send(sender);
				return;
			}

            Preset preset;
            try {
                preset = Preset.save(args[1]);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            
			if (preset != null)
			    Messages.COMMANDS_VARO_PRESET_SAVED.send(sender, Placeholder.constant("preset", args[1]));
			else
			    Messages.COMMANDS_VARO_PRESET_NAME_INVALID.send(sender, Placeholder.constant("preset", args[1]));
		} else if (args[0].equalsIgnoreCase("list")) {
			Messages.COMMANDS_VARO_PRESET_LIST.send(sender);
            Preset.listPresets().forEach(preset -> sender.sendMessage(Main.getPrefix() + preset.getName()));
		} else
		    Messages.COMMANDS_ERROR_USAGE.send(sender, Placeholder.constant("command", "preset"));

	}
}