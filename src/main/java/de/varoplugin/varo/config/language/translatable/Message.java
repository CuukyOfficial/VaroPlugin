package de.varoplugin.varo.config.language.translatable;

import java.util.Arrays;
import java.util.Map;

import org.bukkit.entity.Player;

import de.varoplugin.varo.config.language.Language;
import de.varoplugin.varo.config.language.component.CompositeMessageComponent;
import de.varoplugin.varo.config.language.minimessage.MiniMessageTranslatableMessageComponent;
import de.varoplugin.varo.config.language.minimessage.MiniMessageTranslatableMessageComponentImpl;
import de.varoplugin.varo.config.language.placeholder.GlobalPlaceholder;

public class Message extends GenericTranslatable<String> implements TranslatableMessageComponent {

	private CompositeMessageComponent[] translations;
	private int defaultTranslation;
	private MiniMessageTranslatableMessageComponent miniComponent;

	public Message(String path, String... localPlaceholderNames) {
		super(path, localPlaceholderNames);
	}

	@Override
	public void init(Language[] languages, int defaultTranslation, Map<String, GlobalPlaceholder> globalPlaceholders, boolean placeholderApiSupport) {
		this.defaultTranslation = defaultTranslation;
		this.translations = Arrays.stream(languages)
				.map(language -> this.createCompositeMessageComponent((String) language.getTranslation(this.getPath()).value(), this.getPlaceholderNames(), globalPlaceholders, placeholderApiSupport))
				.toArray(CompositeMessageComponent[]::new);
	}
	
	protected CompositeMessageComponent createCompositeMessageComponent(String translation, String[] localPlaceholders, Map<String, GlobalPlaceholder> globalPlaceholders, boolean placeholderApiSupport) {
		return new CompositeMessageComponent(translation, localPlaceholders, globalPlaceholders, placeholderApiSupport);
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

	@Override
	public boolean shouldEscape() {
		return false;
	}

	@Override
	public MiniMessageTranslatableMessageComponent miniMessage() {
		return this.miniComponent == null ? this.miniComponent = new MiniMessageTranslatableMessageComponentImpl(this.translations, this.defaultTranslation) : this.miniComponent;
	}
}
