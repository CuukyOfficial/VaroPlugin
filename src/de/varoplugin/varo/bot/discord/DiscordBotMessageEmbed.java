package de.varoplugin.varo.bot.discord;

import de.varoplugin.varo.config.language.translatable.TranslatableMessageComponent;

public abstract class DiscordBotMessageEmbed {

	private final TranslatableMessageComponent title, body;
	
	public DiscordBotMessageEmbed(String path, String... localPlaceholderNames) {
		this.title = this.createMessageComponent(path + ".title", localPlaceholderNames);
		this.body = this.createMessageComponent(path + ".body", localPlaceholderNames);
	}
	
	protected abstract TranslatableMessageComponent createMessageComponent(String path, String... localPlaceholderNames);

	public TranslatableMessageComponent title() {
		return this.title;
	}
	
	public TranslatableMessageComponent body() {
		return this.body;
	}
}
