package de.cuuky.varo.logger.logger;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.logger.VaroLogger;

public class BlockLogger extends VaroLogger {

	public BlockLogger(String name) {
		super(name, true);
	}

	public void println(Block block, Player player) {
		if (!Main.getDataManager().getListManager().getDestroyedBlocks().shallLog(block))
			return;

		String log = "[" + getCurrentDate() + "] " + player.getName() + " mined " + block.getType().toString() + " at x:" + block.getLocation().getBlockX() + " y:" + block.getLocation().getBlockY() + " z:" + block.getLocation().getBlockZ() + " in the world '" + block.getWorld().getName() + "'!";

		pw.println(log);
		logs.add(log);

		pw.flush();
	}
}