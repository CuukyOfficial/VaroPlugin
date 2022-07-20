package de.varoplugin.varo.config.language;

public class EnglishLanguage extends AbstractLanguage {
	
	private static final String NAME = "en";

	public EnglishLanguage(int id) {
		super(id, NAME, getDefaultValues());
	}
	
	private static Translation<?>[] getDefaultValues() {
		return new Translation<?>[] {
			new StringTranslation("bot.discord.notverified", "§7You are not verified on our Discord!\n§7Please join our Discord and use §c/verify §7to verify your account!\nLink: §c%discord%\n§7Your code: §c%code%"),
			new StringTranslation("bot.discord.member", "§7You are not a member of our Discord!\n%discord%"),
			new StringTranslation("bot.discord.command.status.title", ""),
			new StringTranslation("bot.discord.command.status.body", "Whitelist: %whitelist%\nGame-State: %gamestate%\nOnline: %online%"),
			new StringTranslation("bot.discord.command.verify.fail.title", ""),
			new StringTranslation("bot.discord.command.verify.fail.body", "Invalid code!"),
			new StringTranslation("bot.discord.command.verify.success.title", ""),
			new StringTranslation("bot.discord.command.verify.success.body", "Your account has been successfully verified!"),
			new StringTranslation("bot.discord.command.verify.alreadyverified.title", ""),
			new StringTranslation("bot.discord.command.verify.alreadyverified.body", "This Discord account has already been verified!"),
			new StringTranslation("bot.discord.modal.verify.title", "Verify"),
			new StringTranslation("bot.discord.modal.verify.inputlabel", "Code:")
		};
	}
}
