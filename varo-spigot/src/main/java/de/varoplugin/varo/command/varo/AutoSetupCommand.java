package de.varoplugin.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.varoplugin.varo.Main;
import de.varoplugin.varo.command.VaroCommand;
import de.varoplugin.varo.config.language.Messages;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;
import de.varoplugin.varo.game.world.AutoSetup;
import de.varoplugin.varo.player.VaroPlayer;

public class AutoSetupCommand extends VaroCommand {

	public AutoSetupCommand() {
		super("autosetup", "Setzt den Server automatisch auf", "varo.autosetup");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if (args.length >= 1) {
			if (args[0].equalsIgnoreCase("run")) {
				if (!ConfigSetting.AUTOSETUP_ENABLED.getValueAsBoolean()) {
					Messages.COMMANDS_VARO_AUTOSETUP_NOT_SETUP_YET.send(sender);
					return;
				}

				new AutoSetup(()-> {
					for (VaroPlayer player : VaroPlayer.getOnlinePlayer())
						player.saveTeleport(Main.getVaroGame().getVaroWorldHandler().getTeleportLocation());

					Messages.COMMANDS_VARO_AUTOSETUP_FINISHED.send(sender);
				});
				return;
			}
		}

		Messages.COMMANDS_VARO_AUTOSETUP_HELP.send(sender);
	}
}