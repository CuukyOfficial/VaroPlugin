package de.varoplugin.varo.config.language;

public class GermanLanguage extends AbstractLanguage {
	
	private static final String NAME = "de";

	public GermanLanguage(int id) {
		super(id, NAME, getDefaultValues());
	}
	
	private static Translation<?>[] getDefaultValues() {
		return new Translation<?>[] {
			new StringTranslation("bot.discord.command.status.title", ""),
			new StringTranslation("bot.discord.command.status.body", "Whitelist: %whitelist%\nGame-State: %gamestate%\nOnline: %online%"),
			new StringTranslation("bot.discord.modal.verify.title", "Verify"),
			new StringTranslation("bot.discord.modal.verify.inputlabel", "Code:")
		};
	}
}
