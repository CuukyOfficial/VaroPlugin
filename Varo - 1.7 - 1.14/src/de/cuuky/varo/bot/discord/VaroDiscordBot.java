package de.cuuky.varo.bot.discord;

import javax.security.auth.login.LoginException;
import java.awt.Color;
import java.io.File;
import java.util.Random;

import de.cuuky.varo.Main;
import de.cuuky.varo.bot.VaroBot;
import de.cuuky.varo.bot.discord.listener.DiscordBotEventListener;
import de.cuuky.varo.config.config.ConfigEntry;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.exceptions.PermissionException;
import net.dv8tion.jda.core.managers.GuildController;

import de.cuuky.varo.Main;
import de.cuuky.varo.bot.VaroBot;
import de.cuuky.varo.bot.discord.listener.DiscordBotEventListener;
import de.cuuky.varo.config.config.ConfigEntry;

public class VaroDiscordBot implements VaroBot {

	private static VaroDiscordBot instance;

	private JDA jda;
	private long registerChannel, eventChannel, announcementChannel, resultChannel, pingRole;

	private VaroDiscordBot() {
	}

	public static VaroDiscordBot getInstance() {
		if (instance == null) {
			instance = new VaroDiscordBot();
		}
		return instance;
	}
	
	public static String getClassName() {
		return JDABuilder.class.getName();
	}

	@Override
	public void connect() {
		System.out.println(Main.getConsolePrefix() + "Activating discord bot... (Errors maybe will appear - don't mind them)");
		JDABuilder builder = new JDABuilder(AccountType.BOT);
		builder.setGame(Game.playing(ConfigEntry.DISCORDBOT_GAMESTATE.getValueAsString()));
		builder.setToken(ConfigEntry.DISCORDBOT_TOKEN.getValueAsString());
		builder.setAutoReconnect(true);
		builder.setRequestTimeoutRetry(true);
		builder.setStatus(OnlineStatus.ONLINE);

		try {
			jda = builder.build();
			jda.addEventListener(new DiscordBotEventListener());
		} catch(Exception | Error e) {
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
		System.out.println(Main.getConsolePrefix() + "DiscordBot enabled successfully!");
	}

	private void loadChannel() {
		try {
			announcementChannel = jda.getTextChannelById(ConfigEntry.DISCORDBOT_ANNOUNCEMENT_CHANNELID.getValueAsLong()).getIdLong();
		} catch (ClassCastException | IllegalArgumentException | NullPointerException e) {
			System.out.println(Main.getConsolePrefix() + "Could not load announcement-channel");
		}

		try {
			eventChannel = jda.getTextChannelById(ConfigEntry.DISCORDBOT_EVENTCHANNELID.getValueAsLong()).getIdLong();
		} catch (ClassCastException | IllegalArgumentException | NullPointerException e) {
			System.out.println(Main.getConsolePrefix() + "Could not load event-channel");
		}

		try {
			resultChannel = jda.getTextChannelById(ConfigEntry.DISCORDBOT_RESULT_CHANNELID.getValueAsLong()).getIdLong();
		} catch (ClassCastException | IllegalArgumentException | NullPointerException e) {
			System.out.println(Main.getConsolePrefix() + "Could not load result-channel");
		}

		try {
			if (ConfigEntry.DISCORDBOT_VERIFYSYSTEM.getValueAsBoolean())
				registerChannel = jda.getTextChannelById(ConfigEntry.DISCORDBOT_REGISTERCHANNELID.getValueAsLong()).getIdLong();
		} catch (ClassCastException | IllegalArgumentException | NullPointerException e) {
			System.out.println(Main.getConsolePrefix() + "Could not load register-channel");
		}

		try {
			pingRole = jda.getRoleById(ConfigEntry.DISCORDBOT_ANNOUNCEMENT_PING_ROLEID.getValueAsLong()).getIdLong();
		} catch (ClassCastException | IllegalArgumentException e) {
			pingRole = -1;
		} catch (NullPointerException e) {
			System.out.println(Main.getConsolePrefix() + "Could not find role for: " + ConfigEntry.DISCORDBOT_ANNOUNCEMENT_PING_ROLEID.getValueAsLong());
		}
	}

	public void sendFile(String message, File file, TextChannel channel) {
		channel.sendFile(file, new MessageBuilder().append(message.replace("_", "\\_")).build()).queue();
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

	public void sendMessage(String message, String title, Color color, long channelid) {
		TextChannel channel = null;
		try {
			channel = jda.getTextChannelById(channelid);
		} catch (Exception e) {
			System.err.println(Main.getConsolePrefix() + "Failed to print message");
		}

		EmbedBuilder builder = new EmbedBuilder();
		if (!ConfigEntry.DISCORDBOT_MESSAGE_RANDOM_COLOR.getValueAsBoolean())
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
			} catch (Exception | Error e1) {
			}
		}

		jda = null;
	}

	private Color getRandomColor() {
		Random random = new Random();
		return new Color(random.nextFloat(), random.nextFloat(), random.nextFloat());
	}

	public boolean isEnabled() {
		return jda != null;
	}

	public JDA getJda() {
		return jda;
	}

	public TextChannel getResultChannel() {
		return jda.getTextChannelById(resultChannel);
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

	public GuildController getController() {
		return new GuildController(jda.getGuildById(ConfigEntry.DISCORDBOT_SERVERID.getValueAsLong()));
	}
}