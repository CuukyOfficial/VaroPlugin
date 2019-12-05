package de.cuuky.varo.logger.logger;

import de.cuuky.varo.data.DataManager;
import de.cuuky.varo.list.ListHandler;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import de.cuuky.varo.logger.Logger;

public class BlockLogger extends Logger {

	private static BlockLogger instance;

	private BlockLogger(String name) {
		super(name, true);
	}

	public static BlockLogger getInstance() {
		if (instance == null) {
			instance = new BlockLogger("blocklogs");
		}
		return instance;
	}

	public void println(Block block, Player player) {
		if(!ListHandler.getInstance().getDestroyedBlocks().shallLog(block))
			return;

		String log = "[" + getCurrentDate() + "] " + player.getName() + " mined " + block.getType().toString() + " at x:" + block.getLocation().getBlockX() + " y:" + block.getLocation().getBlockY() + " z:" + block.getLocation().getBlockZ() + " in the world '" + block.getWorld().getName() + "'!";

		pw.println(log);
		logs.add(log);

		pw.flush();
	}
}