package de.cuuky.varo.logger;

import de.cuuky.varo.logger.logger.BlockLogger;
import de.cuuky.varo.logger.logger.ChatLogger;
import de.cuuky.varo.logger.logger.EventLogger;

public class LoggerMaster {

	private BlockLogger blockLogger;
	private EventLogger fileLogger;
	private ChatLogger chatLogger;

	public LoggerMaster() {
		blockLogger = new BlockLogger("blocklogs");
		fileLogger = new EventLogger("logs");
		chatLogger = new ChatLogger("chatlogs");
	}

	public BlockLogger getBlockLogger() {
		return blockLogger;
	}

	public EventLogger getEventLogger() {
		return fileLogger;
	}

	public ChatLogger getChatLogger() {
		return chatLogger;
	}
}