package de.varoplugin.varo.bot;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import de.varoplugin.varo.Main;
import de.varoplugin.varo.bot.discord.VaroDiscordBot;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;

public class BotLauncher {

	private VaroDiscordBot discordbot;

	public BotLauncher() {
		startupDiscord();
	}

	public void disconnect(long duration, TimeUnit timeUnit) {
		if (discordbot != null)
			discordbot.disconnect(duration, timeUnit);
	}

	public void startupDiscord() {
		if (!ConfigSetting.DISCORDBOT_ENABLED.getValueAsBoolean())
			return;

		if (ConfigSetting.DISCORDBOT_TOKEN.getValue().equals("ENTER TOKEN HERE") || ConfigSetting.DISCORDBOT_SERVERID.getValueAsLong() == -1) {
			Main.getInstance().getLogger().log(Level.SEVERE, "Discord Bot is disabled because of missing information in the config (DiscordToken or ServerID missing)");
			return;
		}

		try {
			discordbot = new VaroDiscordBot();
		} catch (NoClassDefFoundError | BootstrapMethodError e) {
			discordbot = null;
			Main.getInstance().getLogger().log(Level.SEVERE, "Discord Bot is disabled because a dependency is missing! Please report this error on our discord " + Main.DISCORD_INVITE);
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