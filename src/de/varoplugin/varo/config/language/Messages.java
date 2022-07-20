package de.varoplugin.varo.config.language;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.varoplugin.varo.bot.discord.CompositeDiscordBotMessageComponent;
import de.varoplugin.varo.bot.discord.DiscordBotMessageEmbed;
import de.varoplugin.varo.config.language.component.CompositeMessageComponent;
import de.varoplugin.varo.config.language.placeholder.GlobalPlaceholder;
import de.varoplugin.varo.config.language.translatable.Message;
import de.varoplugin.varo.config.language.translatable.Translatable;
import de.varoplugin.varo.config.language.translatable.TranslatableMessageArray;
import de.varoplugin.varo.config.language.translatable.TranslatableMessageComponent;

public class Messages {

	private final List<Translatable<?>> messages = new ArrayList<>();

	public final TranslatableMessageComponent bot_discord_notverified = new VaroMessage("bot.discord.notverified", "code");
	public final TranslatableMessageComponent bot_discord_notmember = new VaroMessage("bot.discord.member");
	public final DiscordBotMessageEmbed bot_discord_command_status = new VaroDiscordBotEmbed("bot.discord.command.status", "whitelist", "gamestate", "online");
	public final DiscordBotMessageEmbed bot_discord_command_verify_fail = new VaroDiscordBotEmbed("bot.discord.command.verify.fail");
	public final DiscordBotMessageEmbed bot_discord_command_verify_success = new VaroDiscordBotEmbed("bot.discord.command.verify.success");
	public final DiscordBotMessageEmbed bot_discord_command_verify_alreadyverified = new VaroDiscordBotEmbed("bot.discord.command.verify.alreadyverified");
	public final TranslatableMessageComponent bot_discord_modal_verify_title = new VaroMessage("bot.discord.modal.verify.title");
	public final TranslatableMessageComponent bot_discord_modal_verify_inputlabel = new VaroMessage("bot.discord.modal.verify.inputlabel");

	public Messages(Language[] languages, int defaultLanguage, Map<String, GlobalPlaceholder> globalPlaceholders, boolean papi) {
		this.messages.forEach(message -> message.init(languages, defaultLanguage, globalPlaceholders, papi));
	}

	public List<Translatable<?>> getMessages() {
		return this.messages;
	}

	private class VaroMessage extends Message {
		private VaroMessage(String path, String... localPlaceholderNames) {
			super(path, localPlaceholderNames);
			Messages.this.getMessages().add(this);
		}
	}

	private class VaroMessageArray extends TranslatableMessageArray {
		private VaroMessageArray(String path, String... localPlaceholderNames) {
			super(path, localPlaceholderNames);
			Messages.this.getMessages().add(this);
		}
	}
	
	private class VaroDiscordBotEmbed extends DiscordBotMessageEmbed {

		public VaroDiscordBotEmbed(String path, String... localPlaceholderNames) {
			super(path, localPlaceholderNames);
		}

		@Override
		protected TranslatableMessageComponent createMessageComponent(String path, String... localPlaceholderNames) {
			return new VaroDiscordBotMessage(path, localPlaceholderNames);
		}
		
	}

	private class VaroDiscordBotMessage extends Message {
		private VaroDiscordBotMessage(String path, String... localPlaceholderNames) {
			super(path, localPlaceholderNames);
			Messages.this.getMessages().add(this);
		}
		
		@Override
		protected CompositeMessageComponent createCompositeMessageComponent(String translation,
				String[] localPlaceholders, Map<String, GlobalPlaceholder> globalPlaceholders,
				boolean placeholderApiSupport) {
			return new CompositeDiscordBotMessageComponent(translation, localPlaceholders, globalPlaceholders, placeholderApiSupport);
		}
	}
}
