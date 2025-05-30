package de.cuuky.varo.logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.scheduler.BukkitRunnable;

import de.cuuky.varo.Main;
import de.cuuky.varo.logger.logger.BlockLogger;
import de.cuuky.varo.logger.logger.ChatLogger;
import de.cuuky.varo.logger.logger.EventLogger;

public class VaroLoggerManager {

	private final List<VaroLogger<?>> loggers;

	private final BlockLogger blockLogger;
	private final ChatLogger chatLogger;
	private final EventLogger eventLogger;

	public VaroLoggerManager() {
		this.loggers = new ArrayList<>();
		this.loggers.add(this.blockLogger = new BlockLogger("blocklogs"));
		this.loggers.add(this.chatLogger = new ChatLogger("chatlogs"));
		this.loggers.add(this.eventLogger = new EventLogger("logs"));

		try {
			for(VaroLogger<?> logger : this.loggers)
				logger.load();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		this.startQueue();
	}

	private void startQueue() {
		new BukkitRunnable() {
			@Override
			public void run() {
				VaroLoggerManager.this.loggers.forEach(VaroLogger::processQueue);
			}
		}.runTaskTimerAsynchronously(Main.getInstance(), 20L, 20L * 60L);
	}

	public void cleanUp() {
		VaroLoggerManager.this.loggers.forEach(VaroLogger::cleanUp);
	}

	public BlockLogger getBlockLogger() {
		return this.blockLogger;
	}

	public ChatLogger getChatLogger() {
		return this.chatLogger;
	}

	public EventLogger getEventLogger() {
		return this.eventLogger;
	}
}