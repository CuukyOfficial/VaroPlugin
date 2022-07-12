package de.varoplugin.varo.api.config.language.translatables;

import java.util.Arrays;
import java.util.Map;

import org.bukkit.entity.Player;

import de.varoplugin.varo.api.config.language.Language;
import de.varoplugin.varo.api.config.language.components.CompositeMessageComponent;
import de.varoplugin.varo.api.config.language.placeholder.GlobalPlaceholder;

public class Message extends GenericTranslatable<String> implements TranslatableMessageComponent {

	private CompositeMessageComponent[] translations;
	private int defaultTranslation;

	public Message(String path, String... localPlaceholderNames) {
		super(path, localPlaceholderNames);
	}

	@Override
	public void init(Language[] languages, int defaultTranslation, Map<String, GlobalPlaceholder> globalPlaceholders, boolean placeholderApiSupport) {
		this.defaultTranslation = defaultTranslation;
		this.translations = Arrays.stream(languages)
				.map(language -> new CompositeMessageComponent((String) language.getTranslation(this.getPath()).value(), this.getPlaceholderNames(), globalPlaceholders, placeholderApiSupport))
				.toArray(CompositeMessageComponent[]::new);
	}

	@Override
	public String value() {
		return this.translations[this.defaultTranslation].value();
	}

	@Override
	public String value(Object... placeholders) {
		return this.translations[this.defaultTranslation].value(placeholders);
	}

	@Override
	public String value(Player player, Object... localPlaceholders) {
		return this.translations[this.defaultTranslation].value(player, localPlaceholders);
	}
	
	@Override
	public String value(Language language) {
		return this.translations[language.getId()].value();
	}

	@Override
	public String value(Language language, Object... localPlaceholders) {
		return this.translations[language.getId()].value(localPlaceholders);
	}

	@Override
	public String value(Language language, Player player,  Object... localPlaceholders) {
		return this.translations[language.getId()].value(player, localPlaceholders);
	}
}
