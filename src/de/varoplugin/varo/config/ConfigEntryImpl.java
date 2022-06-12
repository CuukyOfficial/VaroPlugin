package de.varoplugin.varo.config;

import de.varoplugin.varo.api.config.ConfigCategory;
import de.varoplugin.varo.api.config.ConfigEntry;
import de.varoplugin.varo.api.config.ConfigException;

import java.awt.*;

public class ConfigEntryImpl<T> implements ConfigEntry<T> {

    private static final String DESCRIPTION_FOOTER = "\nDefault value: %s (%s)";

	private final ConfigCategory category;
	private final String path;
	private final T defaultValue;
	private final String description;
	private T value;

	public ConfigEntryImpl(ConfigCategory category, String path, T defaultValue, String description) {
		if (category == null || path == null || defaultValue == null || description == null)
			throw new IllegalArgumentException();

		this.category = category;
		this.path = path;
		this.defaultValue = defaultValue;
		this.description = description + String.format(DESCRIPTION_FOOTER, defaultValue, defaultValue.getClass().getSimpleName());
	}

	private void checkType(Class<?> type) throws ConfigException {
		if (this.defaultValue.getClass() != type && (List.class.isAssignableFrom(this.defaultValue.getClass()) || List.class.isAssignableFrom(type)))
			throw new ConfigException(String.format("Type missmatch! Expected: %s Actual: %s for %s", this.defaultValue.getClass().getName(), type.getName(), this.path));
	}

	@Override
	public ConfigCategory getCategory() {
		return this.category;
	}

	@Override
	public String getPath() {
		return this.path;
	}

	@Override
	public T getDefaultValue() {
		return this.defaultValue;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public T getValue() {
		return this.value;
	}

	@Override
	public void setValue(T value) {
		this.value = value;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setValueAsObject(Object value) throws ConfigException {
		if (value.getClass() == Integer.class && this.defaultValue.getClass() == Long.class)
			value = ((Integer) value).longValue();
		
		this.checkType(value.getClass());
		this.value = (T) value;
	}
}
