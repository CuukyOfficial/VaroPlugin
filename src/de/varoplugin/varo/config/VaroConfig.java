package de.varoplugin.varo.config;

import static de.varoplugin.varo.config.VaroConfigCategory.*;

import de.varoplugin.varo.VaroJavaPlugin;
import de.varoplugin.varo.api.config.ConfigEntry;

public class VaroConfig extends AbstractConfig {

	// These fields do not follow java conventions to improve readability
	public final ConfigEntry<Boolean> offlinemode = new ConfigEntryImpl<>(MAIN, "offlinemode", false, "Whether the server is running in offline mode");
	
	public final ConfigEntry<Boolean> bot_discord_enabled = new ConfigEntryImpl<>(BOTS, "bot.discord.enabled", true, "TODO");
	public final ConfigEntry<String> bot_discord_token = new ConfigEntryImpl<>(BOTS, "bot.discord.token", "INSERT BOT TOKEN HERE", "TODO");
	public final ConfigEntry<Long> bot_discord_guild = new ConfigEntryImpl<>(BOTS, "bot.discord.guild", -1L, "TODO");
	public final ConfigEntry<String> bot_discord_invite = new ConfigEntryImpl<>(BOTS, "bot.discord.invite", VaroJavaPlugin.DISCORD_INVITE, "TODO");
	public final ConfigEntry<String> bot_discord_status = new ConfigEntryImpl<>(BOTS, "bot.discord.status", VaroJavaPlugin.WEBSITE, "TODO");
	public final ConfigEntry<Long> bot_discord_channel_alert = new ConfigEntryImpl<>(BOTS, "bot.discord.channels.alert", -1L, "TODO");
	public final ConfigEntry<Long> bot_discord_channel_border = new ConfigEntryImpl<>(BOTS, "bot.discord.channels.border", -1L, "TODO");
	public final ConfigEntry<Long> bot_discord_channel_death = new ConfigEntryImpl<>(BOTS, "bot.discord.channels.death", -1L, "TODO");
	public final ConfigEntry<Long> bot_discord_channel_join = new ConfigEntryImpl<>(BOTS, "bot.discord.channels.join", -1L, "TODO");
	public final ConfigEntry<Long> bot_discord_channel_leave = new ConfigEntryImpl<>(BOTS, "bot.discord.channels.leave", -1L, "TODO");
	public final ConfigEntry<Long> bot_discord_channel_kill = new ConfigEntryImpl<>(BOTS, "bot.discord.channels.kill", -1L, "TODO");
	public final ConfigEntry<Long> bot_discord_channel_strike = new ConfigEntryImpl<>(BOTS, "bot.discord.channels.strike", -1L, "TODO");
	public final ConfigEntry<Long> bot_discord_channel_win = new ConfigEntryImpl<>(BOTS, "bot.discord.channels.win", -1L, "TODO");
	public final ConfigEntry<Long> bot_discord_channel_youtube = new ConfigEntryImpl<>(BOTS, "bot.discord.channels.youtube", -1L, "TODO");
	public final ConfigEntry<Long> bot_discord_channel_result = new ConfigEntryImpl<>(BOTS, "bot.discord.channels.result", -1L, "TODO");
	public final ConfigEntry<Boolean> bot_discord_embed_enabled = new ConfigEntryImpl<>(BOTS, "bot.discord.embed.enabled", true, "TODO");
	public final ConfigEntry<Boolean> bot_discord_embed_randomcolor = new ConfigEntryImpl<>(BOTS, "bot.discord.embed.randomcolor", true, "TODO");
	public final ConfigEntry<String> bot_discord_modal_verify_title = new ConfigEntryImpl<>(BOTS, "bot.discord.modal.verify.title", "Verify", "TODO");
	public final ConfigEntry<String> bot_discord_modal_verify_input_label = new ConfigEntryImpl<>(BOTS, "bot.discord.modal.verify.inputlabel", "Code:", "TODO");
	public final ConfigEntry<Boolean> bot_discord_command_verify_enabled = new ConfigEntryImpl<>(BOTS, "bot.discord.command.verify.enabled", true, "TODO");
	public final ConfigEntry<String> bot_discord_command_verify_name = new ConfigEntryImpl<>(BOTS, "bot.discord.command.verify.name", "verify", "TODO");
	public final ConfigEntry<String> bot_discord_command_verify_desc = new ConfigEntryImpl<>(BOTS, "bot.discord.command.verify.desc", "TODO", "TODO");
	public final ConfigEntry<Boolean> bot_discord_command_status_enabled = new ConfigEntryImpl<>(BOTS, "bot.discord.command.status.enabled", true, "TODO");
	public final ConfigEntry<String> bot_discord_command_status_name = new ConfigEntryImpl<>(BOTS, "bot.discord.command.status.name", "status", "TODO");
	public final ConfigEntry<String> bot_discord_command_status_desc = new ConfigEntryImpl<>(BOTS, "bot.discord.command.status.desc", "TODO", "TODO");
	
	public final ConfigEntry<Boolean> scoreboard_enabled = new ConfigEntryImpl<>(SCOREBOARD, "scoreboard.enabled", true, "Whether the scoreboard should be enabled (Players may still be able to hide their scoreboard)");
	public final ConfigEntry<Integer> scoreboard_title_delay = new ConfigEntryImpl<>(SCOREBOARD, "scoreboard.title.updatedelay", 100, "The update interval of the title animation");
	public final ConfigEntry<Integer> scoreboard_content_delay = new ConfigEntryImpl<>(SCOREBOARD, "scoreboard.content.updatedelay", 100, "The update interval of the animation");

	public VaroConfig(String path) {
		super(path);
	}
}
