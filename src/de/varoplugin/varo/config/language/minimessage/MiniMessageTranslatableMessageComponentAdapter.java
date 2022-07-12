package de.varoplugin.varo.config.language.minimessage;

import org.bukkit.entity.Player;

import de.varoplugin.varo.config.language.Language;
import de.varoplugin.varo.config.language.component.CompositeMessageComponent;
import net.kyori.adventure.text.Component;

public class MiniMessageTranslatableMessageComponentAdapter implements MiniMessageTranslatableMessageComponent {

	private final CompositeMessageComponent[] translations;
	private final int defaultTranslation;

	public MiniMessageTranslatableMessageComponentAdapter(CompositeMessageComponent[] translations, int defaultTranslation) {
		this.translations = translations;
		this.defaultTranslation = defaultTranslation;
	}

	@Override
	public Component value() {
		return this.translations[this.defaultTranslation].miniMessage().value();
	}

	@Override
	public Component value(Object... placeholders) {
		return this.translations[this.defaultTranslation].miniMessage().value(placeholders);
	}

	@Override
	public Component value(Player player, Object... localPlaceholders) {
		return this.translations[this.defaultTranslation].miniMessage().value(player, localPlaceholders);
	}

	@Override
	public Component value(Language language, Object... localPlaceholders) {
		return this.translations[language.getId()].miniMessage().value(localPlaceholders);
	}

	@Override
	public Component value(Language language, Player player,  Object... localPlaceholders) {
		return this.translations[language.getId()].miniMessage().value(player, localPlaceholders);
	}
}
