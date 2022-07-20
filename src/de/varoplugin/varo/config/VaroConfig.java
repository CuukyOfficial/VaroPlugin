package de.varoplugin.varo.config;

import static de.varoplugin.varo.config.VaroConfigCategory.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.varoplugin.varo.VaroJavaPlugin;
import de.varoplugin.varo.VaroPlugin;
import de.varoplugin.varo.api.config.ConfigCategory;
import de.varoplugin.varo.api.config.ConfigEntry;

public class VaroConfig extends ClassLoaderConfig {

	final List<ConfigEntry<?>> configEntries = new ArrayList<>();

	// These fields do not follow java conventions to improve readability
	public final VaroBoolConfigEntry offlinemode = new VaroBoolConfigEntry(MAIN, "offlinemode", false, "Whether the server is running in offline mode");
	public final ConfigEntry<String> defaultlanguage = new VaroConfigEntry<>(MAIN, "defaultlang", "en", "The default language");
	public final VaroBoolConfigEntry placeholderapi = new VaroBoolConfigEntry(MAIN, "placeholderapi", false, "Whether PlaceholderAPI support is enabled. An external plugin is required");
	public final VaroBoolConfigEntry minimessage = new VaroBoolConfigEntry(MAIN, "minimessage", false, "Whether MiniMessage support is enabled. An external plugin is required");
	public final ConfigEntry<String> db_type = new VaroConfigEntry<>(MAIN, "db.type", "h2", "TODO");
	public final VaroBoolConfigEntry db_h2_inmemory = new VaroBoolConfigEntry(MAIN, "db.h2.inmemory", false, "DO NOT ACTIVATE THIS UNLESS YOU'RE A DEVELOPER AND ABSOLUTELY KNOW WHAT IT DOES");
	public final ConfigEntry<String> db_mysql_url = new VaroConfigEntry<>(MAIN, "db.mysql.url", "localhost:3306", "TODO");
	public final ConfigEntry<String> db_mysql_user = new VaroConfigEntry<>(MAIN, "db.mysql.user", "root", "TODO");
	public final ConfigEntry<String> db_mysql_password = new VaroConfigEntry<>(MAIN, "db.mysql.password", "1234", "TODO");

	public final VaroBoolConfigEntry bot_discord_enabled = new VaroBoolConfigEntry(BOTS, "bot.discord.enabled", false, "Whether the Discord Bot should be enabled");
	public final ConfigEntry<String> bot_discord_token = new VaroConfigEntry<>(BOTS, "bot.discord.token", "INSERT BOT TOKEN HERE", "The Bot Token");
	public final ConfigEntry<Long> bot_discord_guild = new VaroConfigEntry<>(BOTS, "bot.discord.guild", -1L, "Your guild id");
	public final ConfigEntry<String> bot_discord_invite = new VaroConfigEntry<>(BOTS, "bot.discord.invite", VaroJavaPlugin.DISCORD_INVITE, "TODO");
	public final ConfigEntry<String> bot_discord_status = new VaroConfigEntry<>(BOTS, "bot.discord.status", VaroJavaPlugin.WEBSITE, "TODO");
	public final VaroBoolConfigEntry bot_discord_verify_enabled = new VaroBoolConfigEntry(BOTS, "bot.discord.verify.enabled", true, "TODO");
	public final VaroBoolConfigEntry bot_discord_verify_optional = new VaroBoolConfigEntry(BOTS, "bot.discord.verify.optional", true, "TODO");
	public final VaroBoolConfigEntry bot_discord_verify_checkmember = new VaroBoolConfigEntry(BOTS, "bot.discord.verify.checkmember", true, "Check whether the player is still a Discord member");
	public final ConfigEntry<Long> bot_discord_channel_alert = new VaroConfigEntry<>(BOTS, "bot.discord.channels.alert", -1L, "TODO");
	public final ConfigEntry<Long> bot_discord_channel_border = new VaroConfigEntry<>(BOTS, "bot.discord.channels.border", -1L, "TODO");
	public final ConfigEntry<Long> bot_discord_channel_death = new VaroConfigEntry<>(BOTS, "bot.discord.channels.death", -1L, "TODO");
	public final ConfigEntry<Long> bot_discord_channel_join = new VaroConfigEntry<>(BOTS, "bot.discord.channels.join", -1L, "TODO");
	public final ConfigEntry<Long> bot_discord_channel_leave = new VaroConfigEntry<>(BOTS, "bot.discord.channels.leave", -1L, "TODO");
	public final ConfigEntry<Long> bot_discord_channel_kill = new VaroConfigEntry<>(BOTS, "bot.discord.channels.kill", -1L, "TODO");
	public final ConfigEntry<Long> bot_discord_channel_strike = new VaroConfigEntry<>(BOTS, "bot.discord.channels.strike", -1L, "TODO");
	public final ConfigEntry<Long> bot_discord_channel_win = new VaroConfigEntry<>(BOTS, "bot.discord.channels.win", -1L, "TODO");
	public final ConfigEntry<Long> bot_discord_channel_youtube = new VaroConfigEntry<>(BOTS, "bot.discord.channels.youtube", -1L, "TODO");
	public final ConfigEntry<Long> bot_discord_channel_result = new VaroConfigEntry<>(BOTS, "bot.discord.channels.result", -1L, "TODO");
	public final VaroBoolConfigEntry bot_discord_embed_enabled = new VaroBoolConfigEntry(BOTS, "bot.discord.embed.enabled", true, "TODO");
	public final VaroBoolConfigEntry bot_discord_embed_randomcolor = new VaroBoolConfigEntry(BOTS, "bot.discord.embed.randomcolor", true, "TODO");
	public final VaroBoolConfigEntry bot_discord_command_verify_enabled = new VaroBoolConfigEntry(BOTS, "bot.discord.command.verify.enabled", true, "TODO");
	public final ConfigEntry<String> bot_discord_command_verify_name = new VaroConfigEntry<>(BOTS, "bot.discord.command.verify.name", "verify", "TODO");
	public final ConfigEntry<String> bot_discord_command_verify_desc = new VaroConfigEntry<>(BOTS, "bot.discord.command.verify.desc", "TODO", "TODO");
	public final VaroBoolConfigEntry bot_discord_command_status_enabled = new VaroBoolConfigEntry(BOTS, "bot.discord.command.status.enabled", true, "TODO");
	public final ConfigEntry<String> bot_discord_command_status_name = new VaroConfigEntry<>(BOTS, "bot.discord.command.status.name", "status", "TODO");
	public final ConfigEntry<String> bot_discord_command_status_desc = new VaroConfigEntry<>(BOTS, "bot.discord.command.status.desc", "TODO", "TODO");

	public final VaroBoolConfigEntry scoreboard_enabled = new VaroBoolConfigEntry(SCOREBOARD, "scoreboard.enabled", true, "Whether the scoreboard should be enabled (Players may still be able to hide their scoreboard)");
	public final ConfigEntry<Integer> scoreboard_title_delay = new VaroConfigEntry<>(SCOREBOARD, "scoreboard.title.updatedelay", 100, "The update interval of the title animation");
	public final ConfigEntry<Integer> scoreboard_content_delay = new VaroConfigEntry<>(SCOREBOARD, "scoreboard.content.updatedelay", 100, "The update interval of the animation");

	public final ConfigEntry<Integer> start_countdown = new VaroConfigEntry<>(MAIN, "main.startCountdown", 30, "The start countdown");
	public final VaroBoolConfigEntry random_team_at_start = new VaroBoolConfigEntry(MAIN, "main.start.randomTeam", false, "TODO");

	/**
	 * This constructor is just for testing
	 * 
	 * @param path The file path
	 */
	public VaroConfig(String path) {
		super(path);

		for(ConfigEntry<?> entry : this.configEntries)
			this.addConfigEntry(entry);
	}

	public VaroConfig(VaroPlugin plugin, File pluginFile, String path) {
		super(plugin, pluginFile, path);

		for(ConfigEntry<?> entry : this.configEntries)
			this.addConfigEntry(entry);
	}



	private class VaroConfigEntry<T> extends ConfigEntryImpl<T> {

		VaroConfigEntry(ConfigCategory category, String path, T defaultValue, String description) {
			super(category, path, defaultValue, description);

			VaroConfig.this.configEntries.add(this);
		}
	}

	public class VaroBoolConfigEntry extends VaroConfigEntry<Boolean> {

		VaroBoolConfigEntry(ConfigCategory category, String path, boolean defaultValue, String description) {
			super(category, path, defaultValue, description);
		}

		public void ifTrue(Runnable runnable) {
			if (this.getValue())
				runnable.run();
		}
	}
}
