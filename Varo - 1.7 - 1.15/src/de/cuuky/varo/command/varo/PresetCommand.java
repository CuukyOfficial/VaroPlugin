package de.cuuky.varo.command.varo;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.configuration.configurations.messages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.preset.PresetLoader;

public class PresetCommand extends VaroCommand {

	public PresetCommand() {
		super("preset", "Command für die Presets", "varo.preset", "presettings", "presets");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if(args.length == 0) {
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_HELP_HEADER.getValue().replace("%category%", "Presets"));
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo preset §7load <PresetPath>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo preset §7save <PresetPath>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo preset §7list");
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_HELP_FOOTER.getValue());
			return;
		}

		if(args[0].equalsIgnoreCase("load")) {
			if(args.length != 2) {
				sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_PRESET_HELP_LOAD.getValue());
				return;
			}

			PresetLoader loader = new PresetLoader(args[1]);
			if(!loader.getFile().isDirectory()) {
				sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_PRESET_NOT_FOUND.getValue().replace("%preset%", args[1]));
				return;
			}

			loader.loadSettings();
			Main.getDataManager().reloadConfig();
			Main.getDataManager().reloadPlayerClients();
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_PRESET_LOADED.getValue().replace("%preset%", args[1]));
		} else if(args[0].equalsIgnoreCase("save")) {
			if(args.length != 2) {
				sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_PRESET_HELP_SAVE.getValue());
				return;
			}

			PresetLoader loader = new PresetLoader(args[1]);
			loader.copyCurrentSettingsTo();
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_PRESET_SAVED.getValue().replace("%preset%", args[1]));
		} else if(args[0].equalsIgnoreCase("list")) {
			File file = new File("plugins/Varo/presets");
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_PRESET_LIST.getValue());
			for(File f : file.listFiles())
				sender.sendMessage(Main.getPrefix() + f.getName());
		} else
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_ERROR_USAGE.getValue().replace("%command%", "preset"));

	}
}