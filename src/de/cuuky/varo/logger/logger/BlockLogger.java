package de.cuuky.varo.logger.logger;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.logger.CachedVaroLogger;

public class BlockLogger extends CachedVaroLogger<LoggedBlock> {

	public BlockLogger(String name) {
		super(name, LoggedBlock.class);
	}

	public void println(Block block, Player player) {
		if (!Main.getDataManager().getListManager().getDestroyedBlocks().shallLog(block))
			return;

		Location location = block.getLocation();
		this.queLog(new LoggedBlock(System.currentTimeMillis(), player.getUniqueId().toString(), player.getName(), block.getType().toString(), location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ()));
	}
}