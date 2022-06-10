package de.varoplugin.varo.bot.discord;

import java.io.File;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.logging.Level;

import de.varoplugin.varo.VaroPlugin;
import de.varoplugin.varo.bot.Bot;
import de.varoplugin.varo.bot.BotChannel;
import de.varoplugin.varo.bot.BotMessage;
import de.varoplugin.varo.bot.BotMessageComponent;
import de.varoplugin.varo.config.VaroConfig;
import de.varoplugin.varo.config.VaroConfigCategory;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;
import net.dv8tion.jda.api.utils.MarkdownSanitizer;

public class DiscordBot extends ListenerAdapter implements Bot {

	private static final String CONFIG_PATH_PREFIX = "bot.discord.channels.";

	private VaroPlugin varo;
	private VaroConfig config;
	private JDA jda;
	private long guildId;
	private final HashMap<String, Long> channelIds = new HashMap<>();

	@Override
	public void init(VaroPlugin varo) {
		this.varo = varo;
		this.config = null; //TODO
		
		if (!this.config.bot_discord_enabled.getValue())
			return;
		
		varo.getLogger().info("Starting Discord Bot...");
		
		if (this.config.bot_discord_token.getValue().equals(this.config.bot_discord_token.getDefaultValue())) {
			varo.getLogger().severe("Discord Bot disabled! Missing token!");
			return;
		}
		
		if (this.config.bot_discord_guild.getValue().equals(this.config.bot_discord_guild.getDefaultValue())) {
			varo.getLogger().severe("Discord Bot disabled! Missing GuildID");
			return;
		}

		this.guildId = this.config.bot_discord_guild.getValue();

		JDABuilder builder = JDABuilder.createDefault(this.config.bot_discord_token.getValue());
		builder.setActivity(Activity.playing(this.config.bot_discord_status.getValue()));
		builder.setStatus(OnlineStatus.ONLINE);

		try {
			this.jda = builder.build();
			varo.getLogger().info("Waiting for the bot to be ready...");
			this.jda.awaitReady();
			this.jda.addEventListener(this);
		} catch (Throwable t) {
			varo.getLogger().log(Level.SEVERE, "Unable to start Discord Bot", t);
			this.jda = null;
			return;
		}

		if (this.jda.getGuildById(this.guildId) == null) {
			varo.getLogger().severe("Invalid GuildID! Disabling Discord Bot...");
			this.shutdown();
		}

		varo.getLogger().info("Discord Bot enabled successfully!");
	}

	@Override
	public void shutdown() {
		this.jda.shutdownNow();
		this.jda = null;
	}

	@Override
	public boolean isEnabled() {
		return this.jda != null;
	}

	@Override
	public void sendFile(BotChannel botChannel, File file, String fileName) {
		if (!isEnabled())
			return;

		this.getChannel(botChannel, channel -> channel.sendFile(file, fileName).queue());
	}

	@Override
	public void sendMessage(BotMessage message, BotChannel botChannel) {
		if (!isEnabled())
			return;

		this.getChannel(botChannel, channel -> {
			if (this.config.bot_discord_embed_enabled.getValue()) {
				channel.sendMessageEmbeds(this.buildEmbed(message)).queue();
			} else
				channel.sendMessage(this.buildMessage(message)).queue();
		});
	}

	public void reply(BotMessage message, IReplyCallback replyCallback) {
		if (this.config.bot_discord_embed_enabled.getValue())
			replyCallback.replyEmbeds(this.buildEmbed(message)).queue();
		else
			replyCallback.reply(this.buildMessage(message)).queue();
	}

	private MessageEmbed buildEmbed(BotMessage message) {
		EmbedBuilder embed = new EmbedBuilder().setDescription(this.buildText(message.getBody()));
		String title = this.buildText(message.getTitle());
		if(!title.isEmpty())
			embed.setTitle(title);

		if (this.config.bot_discord_embed_randomcolor.getValue())
			embed.setColor(BotMessage.getRandomColor());
		else
			embed.setColor(message.getColor());
		return embed.build();
	}
	
	private String buildMessage(BotMessage message) {
		StringBuilder stringBuilder = new StringBuilder();
		String title = this.buildText(message.getTitle());
		if(!title.isEmpty())
			stringBuilder.append("**").append(title).append("**\n");
		String body = this.buildText(message.getTitle());
		if(!body.isEmpty())
			stringBuilder.append(body);
		return stringBuilder.toString();
	}

	private String buildText(BotMessageComponent... components) {
		StringBuilder stringBuilder = new StringBuilder();
		for (BotMessageComponent component : components)
			stringBuilder.append(component.isEscape() ? MarkdownSanitizer.escape(component.getContent()) : component.getContent());
		return stringBuilder.toString();
	}

	private long getChannelId(BotChannel botChannel) {
		return this.channelIds.computeIfAbsent(botChannel.getPath(), path -> {
			return (long) config.getEntry(VaroConfigCategory.BOTS, CONFIG_PATH_PREFIX + path).getValue();
		});
	}

	private void getChannel(BotChannel botChannel, Consumer<TextChannel> callback) {
		long id = this.getChannelId(botChannel);
		if (id == -1)
			return;
		
		TextChannel channel = this.jda.getTextChannelById(id);
		if (channel != null) {
			if (channel.getGuild().getIdLong() == this.guildId)
				try {
					callback.accept(channel);
				} catch (InsufficientPermissionException e) {
					this.varo.getLogger().warning("The Discord bot is unable to interact with a channel because of a missing permission: " + e.getPermission());
				}
			else
				this.varo.getLogger().warning("Discord channel belongs to a different guild (id: " + id + ", name: " + botChannel.getPath() + ")");
		} else
			this.varo.getLogger().warning("Missing Discord channel (id: " + id + ", name: " + botChannel.getPath() + ")");
	}

	VaroPlugin getVaro() {
		return this.varo;
	}
}
