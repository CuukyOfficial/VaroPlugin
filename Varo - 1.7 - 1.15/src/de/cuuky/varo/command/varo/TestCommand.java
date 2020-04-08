package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.game.world.border.decrease.DecreaseReason;

public class TestCommand extends VaroCommand {

	public TestCommand() {
		super("test", "Test lol", "varo.test");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		Main.getVaroGame().getVaroWorldHandler().decreaseBorder(DecreaseReason.DEATH);
	}
}