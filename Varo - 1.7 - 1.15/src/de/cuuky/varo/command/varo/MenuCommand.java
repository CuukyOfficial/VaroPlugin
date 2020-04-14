package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.configuration.configurations.messages.language.languages.LanguageDE;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.gui.MainMenu;

public class MenuCommand extends VaroCommand {

	public MenuCommand() {
		super("menu", "Öffnet das Menue", null, "gui", "settings");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(Main.getPrefix() + Main.getLanguageManager().getValue(LanguageDE.VARO_COMMANDS_ERROR_NO_CONSOLE, vp));
			return;
		}

		new MainMenu((Player) sender);
	}
}