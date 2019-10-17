package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.world.border.DecreaseReason;

public class TestCommand extends VaroCommand {

	public TestCommand() {
		super("test", "Test Command", "varo.test", null);
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if(!vp.getPlayer().isOp())
			return;

		if(args[0].equalsIgnoreCase("1"))
			Main.getDataManager().getWorldHandler().getBorder().decrease(DecreaseReason.DEATH);

		if(args[0].equalsIgnoreCase("2"))
			Main.getDataManager().getWorldHandler().getBorder().decrease(DecreaseReason.TIME_MINUTES);

		if(args[0].equalsIgnoreCase("3"))
			Main.getDataManager().getWorldHandler().getBorder().decrease(DecreaseReason.TIME_DAYS);

		sender.sendMessage("u suck");
	}
}
