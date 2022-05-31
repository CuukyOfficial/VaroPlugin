package de.cuuky.varo.bot;

import java.io.File;

import de.cuuky.varo.Varo;

public interface Bot {

	void init(Varo varo);

	void shutdown();
	
	boolean isEnabled();

	void sendFile(BotChannel botChannel, File file, String fileName);

	void sendMessage(BotMessage message, BotChannel botChannel);
}