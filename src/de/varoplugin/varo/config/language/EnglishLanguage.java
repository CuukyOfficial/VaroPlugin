package de.varoplugin.varo.config.language;

import java.util.HashMap;

public class EnglishLanguage extends AbstractLanguage {

	private static final String NAME = "en";

	public EnglishLanguage(int id) {
		super(id, NAME, getDefaultValues());
	}

	private static Translation<?>[] getDefaultValues() {
		HashMap<Integer, String> intro = new HashMap<>();
		intro.put(0, "§3GO!!!");
		intro.put(1, "§3%startcountdown%§7...");
		intro.put(2, "§3%startcountdown%§7...");
		intro.put(3, "§3%startcountdown%§7...");
		intro.put(4, "§3%startcountdown%§7...");
		intro.put(5, "§3%startcountdown%§7...");
		intro.put(10, "§3%startcountdown%§7...");
		intro.put(20, "§3%startcountdown%§7...");
		intro.put(30, "§3%projectname% §7starts in §3%startcountdown% §7seconds");

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
			new StringTranslation("bot.discord.modal.verify.inputlabel", "Code:"),

			new IntStringMapTranslation("start.countdownmessages", intro)
		};
	}
}
