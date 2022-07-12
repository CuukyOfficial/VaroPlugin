package de.varoplugin.varo.config.language.translatable;

import org.bukkit.entity.Player;

import de.varoplugin.varo.config.language.Language;
import de.varoplugin.varo.config.language.LanguageContainer;
import de.varoplugin.varo.config.language.component.MessageComponent;

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
