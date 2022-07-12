package de.varoplugin.varo.api.config.language.translatables;

import java.util.Arrays;
import java.util.Map;

import de.varoplugin.varo.api.config.language.Language;
import de.varoplugin.varo.api.config.language.components.CompositeMessageComponent;
import de.varoplugin.varo.api.config.language.components.MessageComponent;
import de.varoplugin.varo.api.config.language.placeholder.GlobalPlaceholder;

public class TranslatableMessageArray extends GenericTranslatable<MessageComponent[]> {

	private MessageComponent[][] translations;
	private int defaultTranslation;

	public TranslatableMessageArray(String path, String... localPlaceholderNames) {
		super(path, localPlaceholderNames);
	}

	@Override
	public void init(Language[] languages, int defaultTranslation, Map<String, GlobalPlaceholder> globalPlaceholders, boolean placeholderApiSupport) {
		this.defaultTranslation = defaultTranslation;
		this.translations = Arrays.stream(languages)
				.map(language -> (String[]) language.getTranslation(this.getPath()).value())
				.map(translations -> Arrays.stream(translations).map(translation -> new CompositeMessageComponent(translation, this.getPlaceholderNames(), globalPlaceholders, placeholderApiSupport)).toArray(CompositeMessageComponent[]::new))
				.toArray(CompositeMessageComponent[][]::new);
	}

	@Override
	public MessageComponent[] value() {
		return this.translations[this.defaultTranslation];
	}

	@Override
	public MessageComponent[] value(Language language) {
		return this.translations[language.getId()];
	}
}
