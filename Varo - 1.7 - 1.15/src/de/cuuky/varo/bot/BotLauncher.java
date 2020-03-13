package de.cuuky.varo.bot;

import de.cuuky.varo.Main;
import de.cuuky.varo.bot.discord.VaroDiscordBot;
import de.cuuky.varo.bot.telegram.VaroTelegramBot;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;

public class BotLauncher {

	private VaroDiscordBot discordbot;
	private VaroTelegramBot telegrambot;

	public BotLauncher() {
		startupDiscord();
		startupTelegram();
	}

	public void disconnect() {
		if(discordbot != null)
			discordbot.disconnect();

		if(telegrambot != null)
			telegrambot.disconnect();
	}

	public void startupDiscord() {
		if(!ConfigSetting.DISCORDBOT_ENABLED.getValueAsBoolean())
			return;

		if(ConfigSetting.DISCORDBOT_TOKEN.getValue().equals("ENTER TOKEN HERE") || ConfigSetting.DISCORDBOT_SERVERID.getValueAsLong() == -1) {
			System.err.println(Main.getConsolePrefix() + "DiscordBot deactivated because of missing information in the config (DiscordToken or ServerID missing)");
			return;
		}

		try {
			discordbot = new VaroDiscordBot();
		} catch(NoClassDefFoundError | BootstrapMethodError e) {
			discordbot = null;
			System.out.println(Main.getConsolePrefix() + "DiscordBot disabled because of missing plugin.");
			System.out.println(Main.getConsolePrefix() + "If you want to use the DiscordBot please install this plugin:");
			System.out.println(Main.getConsolePrefix() + "https://www.spigotmc.org/resources/66778/");
			return;
		}

		try {
			discordbot.connect();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void startupTelegram() {
		if(!ConfigSetting.TELEGRAM_ENABLED.getValueAsBoolean())
			return;

		if(ConfigSetting.TELEGRAM_BOT_TOKEN.getValue().equals("ENTER TOKEN HERE")) {
			System.err.println(Main.getConsolePrefix() + "TelegramBot deactivated because of missing information in the config");
			return;
		}

		try {
			telegrambot = new VaroTelegramBot();
		} catch(NoClassDefFoundError | BootstrapMethodError e) {
			telegrambot = null;
			System.out.println(Main.getConsolePrefix() + "TelegramBot disabled because of missing plugin.");
			System.out.println(Main.getConsolePrefix() + "If you want to use the TelegramBot please install this plugin:");
			System.out.println(Main.getConsolePrefix() + "https://www.spigotmc.org/resources/66823/");
			return;
		}

		try {
			telegrambot.connect();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public VaroDiscordBot getDiscordbot() {
		return this.discordbot;
	}
	
	public VaroTelegramBot getTelegrambot() {
		return this.telegrambot;
	}
}