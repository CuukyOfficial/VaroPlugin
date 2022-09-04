package de.varoplugin.varo.config.language;

public abstract class GenericTranslation<T> implements Translation<T> {

	private final String path;
	protected T value;

	public GenericTranslation(String path, T value) {
		this.path = path;
		this.value = value;
	}

	@Override
	public String path() {
		return this.path;
	}

	@Override
	public T value() {
		return this.value;
	}
}
