package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.gui.admin.game.GameOptionsGUI;

public class GameCommand extends VaroCommand {

	public GameCommand() {
		super("game", "Öffnet das Spieloptionen Menue", "varo.game");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_ERROR_NO_CONSOLE.getValue(vp));
			return;
		}

		new GameOptionsGUI(vp.getPlayer());
	}
}