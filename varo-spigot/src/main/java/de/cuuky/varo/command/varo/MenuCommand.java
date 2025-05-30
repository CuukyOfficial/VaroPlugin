package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.config.language.Messages;
import de.cuuky.varo.gui.MainMenu;
import de.cuuky.varo.player.VaroPlayer;

public class MenuCommand extends VaroCommand {

	public MenuCommand() {
		super("menu", "Öffnet das Menue", null, "gui", "settings");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			Messages.COMMANDS_ERROR_NO_CONSOLE.send(sender);
			return;
		}

		new MainMenu((Player) sender);
	}
}