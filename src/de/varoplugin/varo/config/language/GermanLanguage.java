package de.varoplugin.varo.config.language;

public class GermanLanguage extends AbstractLanguage {
	
	private static final String NAME = "de";

	public GermanLanguage(int id) {
		super(id, NAME, getDefaultValues());
	}
	
	private static Translation<?>[] getDefaultValues() {
		return new Translation<?>[] {
			new StringTranslation("bot.discord.notverified", "§7Du bist noch nicht mit Discord verifiziert!\n§7Um dich zu verifizieren, nutze §c/verify §7auf dem Discord!\nLink zum Discord: §c%discord%\n§7Dein code: §c%code%"),
			new StringTranslation("bot.discord.member", "§7Du bist kein Mitglied unseres Discords!\n%discord%"),
			new StringTranslation("bot.discord.command.status.title", ""),
			new StringTranslation("bot.discord.command.status.body", "Whitelist: %whitelist%\nGame-State: %gamestate%\nOnline: %online%"),
			new StringTranslation("bot.discord.command.verify.fail.title", ""),
			new StringTranslation("bot.discord.command.verify.fail.body", "Ungültiger Verifizierungscode!"),
			new StringTranslation("bot.discord.command.verify.success.title", ""),
			new StringTranslation("bot.discord.command.verify.success.body", "Du hast dich Erfolgreich verifiziert!"),
			new StringTranslation("bot.discord.command.verify.alreadyverified.title", ""),
			new StringTranslation("bot.discord.command.verify.alreadyverified.body", "Dieser Discord Account ist bereits verifiziert!"),
			new StringTranslation("bot.discord.modal.verify.title", "Verify"),
			new StringTranslation("bot.discord.modal.verify.inputlabel", "Code:")
		};
	}
}
