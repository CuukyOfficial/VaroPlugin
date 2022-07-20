package de.varoplugin.varo.config.language.translatable;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import de.varoplugin.varo.config.language.Language;
import de.varoplugin.varo.config.language.component.CompositeMessageComponent;
import de.varoplugin.varo.config.language.component.MessageComponent;
import de.varoplugin.varo.config.language.placeholder.GlobalPlaceholder;

public class TranslatableMessageMap<T> extends GenericTranslatable<Map<T, MessageComponent>> {

	private Map<T, MessageComponent>[] translations;
	private int defaultTranslation;

	public TranslatableMessageMap(String path, String... localPlaceholderNames) {
		super(path, localPlaceholderNames);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void init(Language[] languages, int defaultTranslation, Map<String, GlobalPlaceholder> globalPlaceholders, boolean placeholderApiSupport) {
		this.defaultTranslation = defaultTranslation;
		this.translations = Arrays.stream(languages)
		.map(language -> (Map<T, String>) language.getTranslation(this.getPath()).value())
		.map(translation -> {
			HashMap<T, MessageComponent> map = new HashMap<>();
			translation.entrySet().forEach(entry -> map.put(entry.getKey(), new CompositeMessageComponent(entry.getValue(), this.getPlaceholderNames(), globalPlaceholders, placeholderApiSupport)));
			return Collections.unmodifiableMap(map);
		})
		.toArray(Map[]::new);
	}

	@Override
	public Map<T, MessageComponent> value() {
		return this.translations[this.defaultTranslation];
	}

	@Override
	public Map<T, MessageComponent> value(Language language) {
		return this.translations[language.getId()];
	}
}
