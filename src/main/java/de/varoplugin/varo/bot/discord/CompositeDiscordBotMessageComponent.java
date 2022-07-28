package de.varoplugin.varo.bot.discord;

import java.util.Map;

import org.bukkit.entity.Player;

import de.varoplugin.varo.config.language.component.CompositeMessageComponent;
import de.varoplugin.varo.config.language.component.MessageComponent;
import de.varoplugin.varo.config.language.placeholder.GlobalPlaceholder;
import net.dv8tion.jda.api.utils.MarkdownSanitizer;

public class CompositeDiscordBotMessageComponent extends CompositeMessageComponent {

	public CompositeDiscordBotMessageComponent(String translation, String[] localPlaceholders, Map<String, GlobalPlaceholder> globalPlaceholders, boolean placeholderApiSupport) {
		super(translation, localPlaceholders, globalPlaceholders, placeholderApiSupport);
	}

	@Override
	protected MessageComponent processComponent(MessageComponent component) {
		return new MessageComponent() {
			
			@Override
			public String value() {
				return this.shouldEscape() ? MarkdownSanitizer.escape(component.value()) : component.value();
			}
			
			@Override
			public String value(Object... localPlaceholders) {
				return this.shouldEscape() ? MarkdownSanitizer.escape(component.value(localPlaceholders)) : component.value(localPlaceholders);
			}
			
			@Override
			public String value(Player player, Object... localPlaceholders) {
				return this.shouldEscape() ? MarkdownSanitizer.escape(component.value(player, localPlaceholders)) : component.value(player, localPlaceholders);
			}
			
			@Override
			public boolean shouldEscape() {
				return component.shouldEscape();
			}
		};
	}
}
