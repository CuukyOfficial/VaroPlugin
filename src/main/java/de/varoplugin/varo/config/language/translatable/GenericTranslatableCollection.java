package de.varoplugin.varo.config.language.translatable;

import de.varoplugin.varo.config.language.Language;

public abstract class GenericTranslatableCollection<T> extends GenericTranslatable<T, T> {

	public GenericTranslatableCollection(String path, String[] localPlaceholderNames) {
		super(path, localPlaceholderNames);
	}

	@Override
	public T value() {
		return this.translations[this.getDefaultTranslation()];
	}

	@Override
	public T value(Language language) {
		return this.translations[language.getId()];
	}
}
