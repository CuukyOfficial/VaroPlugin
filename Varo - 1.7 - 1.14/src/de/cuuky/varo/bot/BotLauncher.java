package de.cuuky.varo.bot;

import de.cuuky.varo.Main;
import de.cuuky.varo.bot.discord.VaroDiscordBot;
import de.cuuky.varo.bot.telegram.VaroTelegramBot;
import de.cuuky.varo.config.config.ConfigEntry;

public class BotLauncher {

	private static BotLauncher instance;

	private static VaroDiscordBot discordbot;
	private static VaroTelegramBot telegrambot;

	private BotLauncher() {
		startupDiscord();
		startupTelegram();
	}

	public static BotLauncher getInstance() {
		if (instance == null) {
			instance = new BotLauncher();
		}
		return instance;
	}

	public static VaroDiscordBot getDiscordBot() {
		return discordbot;
	}

	public static VaroTelegramBot getTelegramBot() {
		return telegrambot;
	}

	public void startupDiscord() {
		if (!ConfigEntry.DISCORDBOT_ENABLED.getValueAsBoolean())
			return;

		if (ConfigEntry.DISCORDBOT_TOKEN.getValue().equals("ENTER TOKEN HERE") || ConfigEntry.DISCORDBOT_EVENTCHANNELID.getValueAsLong() == -1 || ConfigEntry.DISCORDBOT_SERVERID.getValueAsLong() == -1) {
			System.err.println(Main.getConsolePrefix() + "DiscordBot deactivated because of missing information in the config");
			return;
		}

		try {
			discordbot = VaroDiscordBot.getInstance();
			discordbot.connect();
		} catch (NoClassDefFoundError | BootstrapMethodError e) {
			discordbot = null;
			System.out.println(Main.getConsolePrefix() + "DiscordBot disabled because of missing plugin.");
			System.out.println(Main.getConsolePrefix() + "If you want to use the discordbot please install this plugin:");
			System.out.println(Main.getConsolePrefix() + "https://www.mediafire.com/file/yzhm845j7ieh678/JDA.jar/file");
		}
	}

	public void startupTelegram() {
		if (!ConfigEntry.TELEGRAM_ENABLED.getValueAsBoolean())
			return;

		if (ConfigEntry.TELEGRAM_BOT_TOKEN.getValue().equals("ENTER TOKEN HERE")) {
			System.err.println(Main.getConsolePrefix() + "TelegramBot deactivated because of missing information in the config");
			return;
		}

		try {
			telegrambot = VaroTelegramBot.getInstance();
			telegrambot.connect();
		} catch (NoClassDefFoundError | BootstrapMethodError e) {
			telegrambot = null;
			System.out.println(Main.getConsolePrefix() + "TelegramBot disabled because of missing plugin.");
			System.out.println(Main.getConsolePrefix() + "If you want to use the Telegrambot please install this plugin:");
			System.out.println(Main.getConsolePrefix() + "https://www.spigotmc.org/resources/66823/");
		}
	}

	public void disconnect() {
		if (discordbot != null)
			discordbot.disconnect();

		if (telegrambot != null)
			telegrambot.disconnect();
	}
}