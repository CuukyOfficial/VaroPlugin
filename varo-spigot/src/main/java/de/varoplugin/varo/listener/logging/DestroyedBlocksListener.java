package de.varoplugin.varo.listener.logging;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import de.varoplugin.varo.Main;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;

public class DestroyedBlocksListener implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void onOreBreak(BlockBreakEvent event) {
		if (!ConfigSetting.BLOCK_DESTROY_LOGGER.getValueAsBoolean())
			return;

		if (event.isCancelled())
			return;

		Main.getDataManager().getVaroLoggerManager().getBlockLogger().println(event.getBlock(), event.getPlayer());
	}
}