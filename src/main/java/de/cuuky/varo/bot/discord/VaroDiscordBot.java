package de.cuuky.varo.bot.discord;

import java.awt.Color;
import java.io.File;
import java.util.Random;

import de.cuuky.varo.Main;
import de.cuuky.varo.bot.VaroBot;
import de.cuuky.varo.bot.discord.listener.DiscordBotEventListener;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.exceptions.PermissionException;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class VaroDiscordBot implements VaroBot {

	private JDA jda;
	private long registerChannel, eventChannel, announcementChannel, resultChannel, pingRole;

	private Color getRandomColor() {
		Random random = new Random();
		return new Color(random.nextFloat(), random.nextFloat(), random.nextFloat());
	}

	@Override
	public void connect() {
		System.out.println(Main.getConsolePrefix() + "Activating discord bot... (Errors might appear - don't mind them)");
		JDABuilder builder = JDABuilder.createLight(ConfigSetting.DISCORDBOT_TOKEN.getValueAsString());
		builder.setActivity(Activity.playing(ConfigSetting.DISCORDBOT_GAMESTATE.getValueAsString()));
		builder.setAutoReconnect(true);
		builder.setRequestTimeoutRetry(true);
		builder.setStatus(OnlineStatus.ONLINE);
		
		if(ConfigSetting.DISCORDBOT_ENABLED_PRIVILIGES.getValueAsBoolean()) {
		builder.setMemberCachePolicy(MemberCachePolicy.ALL);
		builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
		builder.setChunkingFilter(ChunkingFilter.ALL);
		}

		try {
			jda = builder.build();
			jda.addEventListener(new DiscordBotEventListener());
		} catch (Exception | Error e) {
			e.printStackTrace();
			System.err.println(Main.getConsolePrefix() + "Couldn't connect to Discord");
			return;
		}

		try {
			System.out.println(Main.getConsolePrefix() + "Waiting for the bot to be ready...");
			jda.awaitReady();
		} catch (Exception e) {
			return;
		}

		loadChannel();
		if (getMainGuild() == null) {
			System.out.println(Main.getConsolePrefix() + "Cannot get server from ID " + ConfigSetting.DISCORDBOT_SERVERID.getValueAsLong());
			disconnect();
		}

		System.out.println(Main.getConsolePrefix() + "DiscordBot enabled successfully!");
	}

	private void loadChannel() {
		try {
			announcementChannel = jda.getTextChannelById(ConfigSetting.DISCORDBOT_ANNOUNCEMENT_CHANNELID.getValueAsLong()).getIdLong();

			if (announcementChannel == -1)
				System.err.println(Main.getConsolePrefix() + "Could not load announcement-channel");
		} catch (ClassCastException | IllegalArgumentException | NullPointerException e) {
			System.err.println(Main.getConsolePrefix() + "Could not load announcement-channel");
		}

		try {
			eventChannel = jda.getTextChannelById(ConfigSetting.DISCORDBOT_EVENTCHANNELID.getValueAsLong()).getIdLong();
		} catch (ClassCastException | IllegalArgumentException | NullPointerException e) {
			System.err.println(Main.getConsolePrefix() + "Could not load event-channel");
		}

		try {
			resultChannel = jda.getTextChannelById(ConfigSetting.DISCORDBOT_RESULT_CHANNELID.getValueAsLong()).getIdLong();

			if (resultChannel == -1)
				System.err.println(Main.getConsolePrefix() + "Could not load result-channel");
		} catch (ClassCastException | IllegalArgumentException | NullPointerException e) {
			System.err.println(Main.getConsolePrefix() + "Could not load result-channel");
		}

		try {
			if (ConfigSetting.DISCORDBOT_VERIFYSYSTEM.getValueAsBoolean())
				registerChannel = jda.getTextChannelById(ConfigSetting.DISCORDBOT_REGISTERCHANNELID.getValueAsLong()).getIdLong();

			if (registerChannel == -1)
				System.err.println(Main.getConsolePrefix() + "Could not load register-channel");
		} catch (ClassCastException | IllegalArgumentException | NullPointerException e) {
			System.err.println(Main.getConsolePrefix() + "Could not load result-channel");
		}

		try {
			pingRole = -1;
			pingRole = jda.getRoleById(ConfigSetting.DISCORDBOT_ANNOUNCEMENT_PING_ROLEID.getValueAsLong()).getIdLong();

			if (pingRole == -1)
				System.err.println(Main.getConsolePrefix() + "Could not find role for: " + ConfigSetting.DISCORDBOT_ANNOUNCEMENT_PING_ROLEID.getValueAsLong());
		} catch (NullPointerException e) {
			System.err.println(Main.getConsolePrefix() + "Could not find role for: " + ConfigSetting.DISCORDBOT_ANNOUNCEMENT_PING_ROLEID.getValueAsLong());
		}
	}

	@Override
	public void disconnect() {
		if (!isEnabled())
			return;

		try {
			jda.shutdownNow();
		} catch (Exception | Error e) {
			System.err.println("[Varo] DiscordBot failed shutting down! Maybe you switched the version while the plugin was running?");
			try {
				jda.shutdown();
			} catch (Exception | Error e1) {}
		}

		jda = null;
	}

	public void sendFile(String message, File file, TextChannel channel) {
		channel.sendFile(file, message.replace("_", "\\_")).queue();
	}

	public void sendMessage(String message, String title, Color color, long channelid) {
		TextChannel channel = null;
		try {
			channel = jda.getTextChannelById(channelid);
		} catch (Exception e) {
			System.err.println(Main.getConsolePrefix() + "Failed to print discord message!");
			return;
		}
		if (ConfigSetting.DISCORDBOT_USE_EMBEDS.getValueAsBoolean()) {
			EmbedBuilder builder = new EmbedBuilder();
			if (!ConfigSetting.DISCORDBOT_MESSAGE_RANDOM_COLOR.getValueAsBoolean())
				builder.setColor(color);
			else
				builder.setColor(getRandomColor());
			builder.addField(title, message.replace("_", "\\_"), true);
			try {
				channel.sendMessage(builder.build()).queue();
			} catch (PermissionException e) {
				System.err.println(Main.getConsolePrefix() + "Bot failed to write a message because of missing permission! MISSING: " + e.getPermission());
				System.err.println(Main.getConsolePrefix() + "On channel " + channel.getName());
			}
		} else {
			try {
				channel.sendMessage(message).queue();
			} catch (PermissionException e) {
				System.err.println(Main.getConsolePrefix() + "Bot failed to write a message because of missing permission! MISSING: " + e.getPermission());
				System.err.println(Main.getConsolePrefix() + "On channel " + channel.getName());
			}
		}
	}

	public void sendMessage(String message, String title, Color color, TextChannel channel) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.setColor(color);
		builder.addField(title, message.replace("_", "\\_"), true);
		try {
			channel.sendMessage(builder.build()).queue();
		} catch (PermissionException e) {
			System.err.println(Main.getConsolePrefix() + "Bot failed to write a message because of missing permission! MISSING: " + e.getPermission());
			System.err.println(Main.getConsolePrefix() + "On channel " + channel.getName());
		}
	}

	public void sendRawMessage(String message, TextChannel channel) {
		if (jda == null || message.isEmpty())
			return;

		try {
			channel.sendMessage(message.replace("_", "\\_")).queue();
		} catch (PermissionException e) {
			System.err.println("Bot failed to write a message because of missing permission! MISSING: " + e.getPermission());
		}
	}

	public String getMentionRole() {
		if (pingRole == -1)
			return "@everyone";

		return jda.getRoleById(pingRole).getAsMention();
	}

	public TextChannel getAnnouncementChannel() {
		return jda.getTextChannelById(announcementChannel);
	}

	public TextChannel getEventChannel() {
		return jda.getTextChannelById(eventChannel);
	}

	public TextChannel getRegisterChannel() {
		return jda.getTextChannelById(registerChannel);
	}

	public TextChannel getResultChannel() {
		return jda.getTextChannelById(resultChannel);
	}

	public Guild getMainGuild() {
		return jda.getGuildById(ConfigSetting.DISCORDBOT_SERVERID.getValueAsLong());
	}

	public JDA getJda() {
		return jda;
	}

	public boolean isEnabled() {
		return jda != null;
	}
}
