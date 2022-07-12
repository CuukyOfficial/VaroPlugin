package de.varoplugin.varo.api.config.language.translatables;

import java.util.Map;

import de.varoplugin.varo.api.config.language.Language;
import de.varoplugin.varo.api.config.language.LanguageContainer;
import de.varoplugin.varo.api.config.language.placeholder.GlobalPlaceholder;

public interface Translatable<T> {

	String getPath();

	String[] getPlaceholderNames();
	
	void init(Language[] languages, int defaultTranslation, Map<String, GlobalPlaceholder> globalPlaceholders, boolean placeholderApiSupport);
	
	T value();
	
	T value(Language language);
	
	default void value(LanguageContainer languageContainer) {
		this.value(languageContainer.getLanguage());
	}
}
