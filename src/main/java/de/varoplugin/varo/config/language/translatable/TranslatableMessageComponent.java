package de.varoplugin.varo.config.language.translatable;

import org.bukkit.entity.Player;

import de.varoplugin.varo.config.language.Language;
import de.varoplugin.varo.config.language.LanguageContainer;
import de.varoplugin.varo.config.language.component.MessageComponent;
import de.varoplugin.varo.config.language.minimessage.MiniMessageTranslatableMessageComponent;

public interface TranslatableMessageComponent extends Translatable<String>, MessageComponent {

	String value(Language language, Object... localPlaceholders);

	String value(Language language, Player player,  Object... localPlaceholders);

	default String value(LanguageContainer languageContainer, Object... localPlaceholders) {
		return this.value(languageContainer.getLanguage(), localPlaceholders);
	}

	default String value(LanguageContainer languageContainer, Player player, Object... localPlaceholders) {
		return this.value(languageContainer.getLanguage(), player, localPlaceholders);
	}

	MiniMessageTranslatableMessageComponent miniMessage();
}
