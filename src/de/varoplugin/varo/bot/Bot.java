package de.varoplugin.varo.bot;

import java.io.File;

import de.varoplugin.varo.VaroPlugin;

public interface Bot {

	void init(VaroPlugin varo);

	void shutdown();
	
	boolean isEnabled();

	void sendFile(BotChannel botChannel, File file, String fileName);

	void sendMessage(BotMessage message, BotChannel botChannel);
}