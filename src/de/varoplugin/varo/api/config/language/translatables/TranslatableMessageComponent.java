package de.varoplugin.varo.api.config.language.translatables;

import org.bukkit.entity.Player;

import de.varoplugin.varo.api.config.language.Language;
import de.varoplugin.varo.api.config.language.LanguageContainer;
import de.varoplugin.varo.api.config.language.components.MessageComponent;

public interface TranslatableMessageComponent extends Translatable<String>, MessageComponent {

	String value(Language language, Object... localPlaceholders);

	String value(Language language, Player player,  Object... localPlaceholders);

	default void value(LanguageContainer languageContainer, Object... localPlaceholders) {
		this.value(languageContainer.getLanguage(), localPlaceholders);
	}

	default void value(LanguageContainer languageContainer, Player player, Object... localPlaceholders) {
		this.value(languageContainer.getLanguage(), player, localPlaceholders);
	}
}
