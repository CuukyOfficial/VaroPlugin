package de.varoplugin.varo.bot;

import java.awt.Color;
import java.util.Random;

public class BotMessage {

	private static final Random RANDOM = new Random();

	private BotMessageComponent[] title = new BotMessageComponent[0];
	private BotMessageComponent[] body = new BotMessageComponent[0];
	private Color color = Color.WHITE;

	public BotMessageComponent[] getTitle() {
		return this.title;
	}

	public BotMessage setTitle(BotMessageComponent... title) {
		this.title = title;
		return this;
	}

	public BotMessageComponent[] getBody() {
		return this.body;
	}

	public BotMessage setBody(BotMessageComponent... body) {
		this.body = body;
		return this;
	}

	public Color getColor() {
		return this.color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public static Color getRandomColor() {
		return new Color(RANDOM.nextFloat(), RANDOM.nextFloat(), RANDOM.nextFloat());
	}
}
