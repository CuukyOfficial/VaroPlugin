package de.varoplugin.varo.api.config.language.translatables;

public abstract class GenericTranslatable<T> implements Translatable<T> {

	private final String path;
	private final String[] localPlaceholderNames;
	
	public GenericTranslatable(String path, String... localPlaceholderNames) {
		this.path = path;
		this.localPlaceholderNames = localPlaceholderNames;
	}

	@Override
	public String getPath() {
		return this.path;
	}

	@Override
	public String[] getPlaceholderNames() {
		return this.localPlaceholderNames;
	}
}
