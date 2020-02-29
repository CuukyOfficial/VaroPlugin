package de.cuuky.varo.logger;

import de.cuuky.varo.logger.logger.BlockLogger;
import de.cuuky.varo.logger.logger.ChatLogger;
import de.cuuky.varo.logger.logger.ConsoleLogger;
import de.cuuky.varo.logger.logger.EventLogger;

public class VaroLoggerManager {
	
	private BlockLogger blockLogger;
	private ChatLogger chatLogger;
	private ConsoleLogger consoleLogger;
	private EventLogger eventLogger;
	
	public VaroLoggerManager() {
		this.blockLogger = new BlockLogger("blocklogs");
		this.chatLogger = new ChatLogger("chatlogs");
		this.eventLogger = new EventLogger("logs");
	}
	
	public BlockLogger getBlockLogger() {
		return this.blockLogger;
	}
	
	public ChatLogger getChatLogger() {
		return this.chatLogger;
	}
	
	public ConsoleLogger getConsoleLogger() {
		return this.consoleLogger;
	}
	
	public EventLogger getEventLogger() {
		return this.eventLogger;
	}
}