/* 
 * VaroPlugin
 * Copyright (C) 2022 Cuuky, Almighty-Satan
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/

package de.varoplugin.varo.bot.discord;

import java.awt.Color;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import de.varoplugin.varo.VaroPlugin;
import de.varoplugin.varo.api.config.ConfigEntry;
import de.varoplugin.varo.bot.Bot;
import de.varoplugin.varo.config.VaroConfig;
import de.varoplugin.varo.config.language.Messages;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public class DiscordBot extends ListenerAdapter implements Bot {

	private VaroPlugin varo;
	private VaroConfig config;
	private Messages messages;
	private JDA jda;
	private long guildId;
	private VerifyManager verifyManager;
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

		if (varo.getVaroConfig().bot_discord_verify_enabled.getValue()) {
			try {
				this.verifyManager = new VerifyManager(varo, this, varo.getConnectionSource());
			} catch (SQLException e) {
				varo.getLogger().log(Level.SEVERE, "Unable to init VerifyManager", e);
				return;
			}
			Bukkit.getPluginManager().registerEvents(this.verifyManager, varo);
		}

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
		if (this.config.bot_discord_command_status_enabled.getValue() && this.config.bot_discord_verify_enabled.getValue())
			this.commands.add(new VerifyCommand(this.config, this.messages));

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

	public void sendMessage(ConfigEntry<Long> channel, DiscordBotMessageEmbed message, Object... localPlaceholders) {
		if (!isEnabled())
			return;

		this.getChannel(channel, textChannel -> {
			if (this.config.bot_discord_embed_enabled.getValue()) {
				textChannel.sendMessageEmbeds(this.buildEmbed(message.body().value(localPlaceholders), message.title().value(localPlaceholders))).queue();
			} else
				textChannel.sendMessage(this.buildMessage(message.body().value(localPlaceholders), message.title().value(localPlaceholders))).queue();
		});
	}

	public void sendMessage(InteractionHook interactionHook, DiscordBotMessageEmbed message, Object... localPlaceholders) {
		this.sendMessage(interactionHook, message.body().value(localPlaceholders), message.title().value(localPlaceholders));
	}

	public void sendMessage(InteractionHook interactionHook, String body, String title) {
		if (this.config.bot_discord_embed_enabled.getValue())
			interactionHook.sendMessageEmbeds(this.buildEmbed(body, title)).queue();
		else
			interactionHook.sendMessage(this.buildMessage(body, title)).queue();
	}

	public void reply(IReplyCallback replyCallback, DiscordBotMessageEmbed message, Object... localPlaceholders) {
		this.reply(replyCallback, message.body().value(localPlaceholders), message.title().value(localPlaceholders));
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

	@Override
	public void onModalInteraction(ModalInteractionEvent event) {
		if (event.getModalId().equals(VerifyCommand.VERIFY_MODAL_ID)) {
			event.deferReply().queue();
			new BukkitRunnable() {

				@Override
				public void run() {
					try {
						if (DiscordBot.this.verifyManager.isVerified(event.getUser().getIdLong())) {
							DiscordBot.this.sendMessage(event.getHook(), DiscordBot.this.varo.getMessages().bot_discord_command_verify_alreadyverified);
							return;
						}
						if (!DiscordBot.this.verifyManager.tryVerify(event.getUser().getIdLong(), event.getValue(VerifyCommand.VERIFY_MODAL_INPUT_ID).getAsString())) {
							DiscordBot.this.sendMessage(event.getHook(), DiscordBot.this.varo.getMessages().bot_discord_command_verify_fail);
							return;
						}
						DiscordBot.this.sendMessage(event.getHook(), DiscordBot.this.varo.getMessages().bot_discord_command_verify_success);
					} catch (SQLException e) {
						DiscordBot.this.varo.getLogger().log(Level.SEVERE, "Unable to verify player", e);
					}
				}
			}.runTaskAsynchronously(this.varo);
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
	
	Guild getGuild() {
		return this.jda.getGuildById(this.guildId);
	}
}
