package de.cuuky.varo.bot;

import de.cuuky.varo.Main;
import de.cuuky.varo.bot.discord.VaroDiscordBot;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;

public class BotLauncher {

	private VaroDiscordBot discordbot;

	public BotLauncher() {
		startupDiscord();
	}

	public void disconnect() {
		if (discordbot != null)
			discordbot.disconnect();
	}

	public void startupDiscord() {
		if (!ConfigSetting.DISCORDBOT_ENABLED.getValueAsBoolean())
			return;

		if (ConfigSetting.DISCORDBOT_TOKEN.getValue().equals("ENTER TOKEN HERE") || ConfigSetting.DISCORDBOT_SERVERID.getValueAsLong() == -1) {
			System.err.println(Main.getConsolePrefix() + "DiscordBot deactivated because of missing information in the config (DiscordToken or ServerID missing)");
			return;
		}

		try {
			discordbot = new VaroDiscordBot();
		} catch (NoClassDefFoundError | BootstrapMethodError e) {
			discordbot = null;
			System.out.println(Main.getConsolePrefix() + "The Discord Bot was disabled because a dependency is missing! Please report this error on our discord " + Main.DISCORD_INVITE);
			e.printStackTrace();
			return;
		}

		try {
			discordbot.connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public VaroDiscordBot getDiscordbot() {
		return this.discordbot;
	}
}