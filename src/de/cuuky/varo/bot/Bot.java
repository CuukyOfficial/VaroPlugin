package de.cuuky.varo.bot;

import java.awt.Color;
import java.io.File;
import java.util.Random;

import de.cuuky.varo.Varo;

public interface Bot {

	void init(Varo varo);

	void shutdown();
	
	boolean isEnabled();

	void sendFile(String message, File file, BotChannel botChannel);

	void sendMessage(String message, String title, Color color, BotChannel botChannel);

	void sendRawMessage(String message, BotChannel botChannel);

	default Color getRandomColor() {
		Random random = new Random();
		return new Color(random.nextFloat(), random.nextFloat(), random.nextFloat());
	}
}