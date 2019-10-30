package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.player.VaroPlayer;

public class ReloadCommand extends VaroCommand {

	public ReloadCommand() {
		super("reload", "Reloaded das Varo Plugin", null);
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		Main.getDataManager().reloadConfig();
		sender.sendMessage(Main.getPrefix() + "§7Das Varo-Plugin wurde erfolgreich reloaded.");
	}
}
