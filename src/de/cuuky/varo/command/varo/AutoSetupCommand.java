package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.game.world.setup.AutoSetup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AutoSetupCommand extends VaroCommand {
	private static final String[] subCommands = {"run"};
	public AutoSetupCommand() {
		super("autosetup", "Setzt den Server automatisch auf", "varo.autosetup", subCommands);
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if (args.length >= 1) {
			if (args[0].equalsIgnoreCase("run")) {
				if (!ConfigSetting.AUTOSETUP_ENABLED.getValueAsBoolean()) {
					sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_AUTOSETUP_NOT_SETUP_YET.getValue(vp));
					return;
				}

				new AutoSetup(()-> {
					for (VaroPlayer player : VaroPlayer.getOnlinePlayer())
						player.saveTeleport(Main.getVaroGame().getVaroWorldHandler().getTeleportLocation());

					sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_AUTOSETUP_FINISHED.getValue(vp));
				});
				return;
			}
		}

		sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_AUTOSETUP_HELP.getValue(vp));
		sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_AUTOSETUP_ATTENTION.getValue(vp));
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		ArrayList<String> list = new ArrayList<>();
		if (args.length == 2 && subCommands != null) {
			List<String> subCommands = Arrays.asList(this.subCommands);
			list.addAll(subCommands);
		}
		ArrayList<String> completerList = new ArrayList<>();
		String curentarg = args[args.length - 1].toLowerCase();
		for (String s : list) {
			String s1 = s.toLowerCase();
			if (s1.startsWith(curentarg)) {
				completerList.add(s);
			}
		}
		return completerList;
	}
}