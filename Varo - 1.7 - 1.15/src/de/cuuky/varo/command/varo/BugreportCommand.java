package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.configuration.configurations.messages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.recovery.recoveries.VaroBugreport;

public class BugreportCommand extends VaroCommand {

	public BugreportCommand() {
		super("bugreport", "Hift bei der Fehlersuche und beim Reporten von Bugs", "varo.bug", "bug", "bughelp", "error");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		VaroBugreport bugreport = new VaroBugreport();

		sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_BUGREPORT_CREATED.getValue().replace("%filename%", bugreport.getZipFile().getName()));
		sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_BUGREPORT_SEND_TO_DISCORD.getValue());
	}
}