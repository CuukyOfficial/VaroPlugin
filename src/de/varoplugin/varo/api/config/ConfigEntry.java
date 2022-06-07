package de.varoplugin.varo.api.config;

public interface ConfigEntry<T> {

	ConfigCategory getCategory();

	String getPath();

	T getDefaultValue();

	String getDescription();

	T getValue();

	void setValue(Object value);
}