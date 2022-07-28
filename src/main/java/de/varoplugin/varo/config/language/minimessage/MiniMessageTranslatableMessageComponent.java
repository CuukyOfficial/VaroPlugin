package de.varoplugin.varo.config.language.minimessage;

import org.bukkit.entity.Player;

import de.varoplugin.varo.config.language.Language;
import de.varoplugin.varo.config.language.LanguageContainer;
import net.kyori.adventure.text.Component;

public interface MiniMessageTranslatableMessageComponent extends MiniMessageMessageComponent {

	Component value(Language language, Object... localPlaceholders);

	Component value(Language language, Player player,  Object... localPlaceholders);

	default Component value(LanguageContainer languageContainer, Object... localPlaceholders) {
		return this.value(languageContainer.getLanguage(), localPlaceholders);
	}

	default Component value(LanguageContainer languageContainer, Player player, Object... localPlaceholders) {
		return this.value(languageContainer.getLanguage(), player, localPlaceholders);
	}
}
