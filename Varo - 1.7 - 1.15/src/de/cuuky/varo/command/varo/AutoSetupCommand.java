package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.messages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.game.world.setup.AutoSetup;

public class AutoSetupCommand extends VaroCommand {

	public AutoSetupCommand() {
		super("autosetup", "Setzt den Server automatisch auf", "varo.autosetup");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if(args.length >= 1) {
			if(args[0].equalsIgnoreCase("run")) {
				if(!ConfigSetting.AUTOSETUP_ENABLED.getValueAsBoolean()) {
					sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_AUTOSETUP_NOT_SETUP_YET.getValue());
					return;
				}

				new AutoSetup();
				for(VaroPlayer player : VaroPlayer.getOnlinePlayer()) 
					player.getPlayer().teleport(Main.getVaroGame().getVaroWorldHandler().getTeleportLocation());
				
				sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_AUTOSETUP_FINISHED.getValue());
				return;
			}
		}

		sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_AUTOSETUP_HELP.getValue());
		sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_AUTOSETUP_ATTENTION.getValue());
	}	
}