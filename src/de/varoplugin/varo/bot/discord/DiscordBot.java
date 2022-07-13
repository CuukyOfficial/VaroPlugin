package de.varoplugin.varo.bot.discord;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.logging.Level;

import de.varoplugin.varo.VaroPlugin;
import de.varoplugin.varo.api.config.ConfigEntry;
import de.varoplugin.varo.bot.Bot;
import de.varoplugin.varo.config.VaroConfig;
import de.varoplugin.varo.config.language.Messages;
import de.varoplugin.varo.config.language.translatable.TranslatableMessageComponent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public class DiscordBot extends ListenerAdapter implements Bot {

	private VaroPlugin varo;
	private VaroConfig config;
	private Messages messages;
	private JDA jda;
	private long guildId;
	private final List<Command> commands = new ArrayList<>();

	@Override
	public void init(VaroPlugin varo) {
		this.varo = varo;
		this.config = varo.getVaroConfig();
		this.messages = varo.getMessages();

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

		Guild guild = this.jda.getGuildById(this.guildId);
		if (guild == null) {
			varo.getLogger().severe("Invalid GuildID! Disabling Discord Bot...");
			this.shutdown();
		}

		this.commands.add(new InfoCommand(varo));
		this.config.bot_discord_command_status_enabled.ifTrue(() -> this.commands.add(new StatusCommand(this.config)));
		this.config.bot_discord_command_status_enabled.ifTrue(() -> this.commands.add(new VerifyCommand(this.config, this.messages)));

		guild.updateCommands().addCommands(this.commands.stream().map(Command::buildCommandData).toArray(CommandData[]::new)).queue();

		varo.getLogger().info("Discord Bot successfully enabled!");
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

	public void sendMessage(ConfigEntry<Long> channel, TranslatableMessageComponent body, TranslatableMessageComponent title, Object... bodyPlaceholders) {
		if (!isEnabled())
			return;

		this.getChannel(channel, textChannel -> {
			if (this.config.bot_discord_embed_enabled.getValue()) {
				textChannel.sendMessageEmbeds(this.buildEmbed(body.value(bodyPlaceholders), title.value())).queue();
			} else
				textChannel.sendMessage(this.buildMessage(body.value(bodyPlaceholders), title.value())).queue();
		});
	}

	public void reply(IReplyCallback replyCallback, TranslatableMessageComponent body, TranslatableMessageComponent title, Object... bodyPlaceholders) {
		this.reply(replyCallback, body.value(bodyPlaceholders), title.value());
	}
	
	public void reply(IReplyCallback replyCallback, String body, String title) {
		if (this.config.bot_discord_embed_enabled.getValue())
			replyCallback.replyEmbeds(this.buildEmbed(body, title)).queue();
		else
			replyCallback.reply(this.buildMessage(body, title)).queue();
	}

	private MessageEmbed buildEmbed(String body, String title) {
		EmbedBuilder embed = new EmbedBuilder().setDescription(body);
		if(!title.isEmpty())
			embed.setTitle(title);

		if (this.config.bot_discord_embed_randomcolor.getValue()) {
			Random random = ThreadLocalRandom.current();
			embed.setColor(new Color(random.nextFloat(), random.nextFloat(), random.nextFloat()));
		} else
			embed.setColor(Color.CYAN);
		return embed.build();
	}

	private String buildMessage(String body, String title) {
		StringBuilder stringBuilder = new StringBuilder();
		if(!title.isEmpty())
			stringBuilder.append("**").append(title).append("**\n");
		if(!body.isEmpty())
			stringBuilder.append(body);
		return stringBuilder.toString();
	}

	private void getChannel(ConfigEntry<Long> channel, Consumer<TextChannel> callback) {
		long id = channel.getValue();
		if (id == -1)
			return;

		TextChannel textChannel = this.jda.getTextChannelById(id);
		if (textChannel != null) {
			if (textChannel.getGuild().getIdLong() == this.guildId)
				try {
					callback.accept(textChannel);
				} catch (InsufficientPermissionException e) {
					this.varo.getLogger().warning("The Discord bot is unable to interact with a channel because of missing permissions: " + e.getPermission());
				}
			else
				this.varo.getLogger().warning("Discord channel belongs to a different guild (id: " + id + ", name: " + channel.getPath() + ")");
		} else
			this.varo.getLogger().warning("Missing Discord channel (id: " + id + ", name: " + channel.getPath() + ")");
	}

	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
		try {
			for (Command command : this.commands)
				if (command.getName().equals(event.getName()))
					command.exec(this, event.getMember(), event);
		}catch(Throwable t) {
			this.varo.getLogger().log(Level.WARNING, "Failed to execute Discord command: " + event.getName(), t);
		}
	}

	VaroPlugin varo() {
		return this.varo;
	}
	
	VaroConfig config() {
		return this.config();
	}
	
	Messages messages() {
		return this.messages;
	}
}
