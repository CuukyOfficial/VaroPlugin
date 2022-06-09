package de.varoplugin.varo.config;

import java.awt.List;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

import org.simpleyaml.configuration.comments.format.YamlCommentFormat;
import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

import de.varoplugin.varo.api.config.Config;
import de.varoplugin.varo.api.config.ConfigCategory;
import de.varoplugin.varo.api.config.ConfigEntry;
import de.varoplugin.varo.api.config.ConfigException;

public abstract class AbstractConfig implements Config {

	private final Multimap<ConfigCategory, ConfigEntry<?>> configEntries = LinkedHashMultimap.create();
	private final String path;

	protected AbstractConfig(String path) {
		this.path = path;
	}

	@Override
	public void addConfigEntry(ConfigEntry<?> configEntry) {
		this.configEntries.put(configEntry.getCategory(), configEntry);
	}

	@Override
	public void load() throws ConfigException, IOException {
		for (ConfigCategory category : this.configEntries.keySet()) {
			YamlFile config = new YamlFile(this.path + category.getFileName());
			config.options().charset(StandardCharsets.UTF_8);
			config.createOrLoadWithComments();
			config.setCommentFormat(YamlCommentFormat.PRETTY);

			boolean changed = false;
			if (!category.getDescription().equals(config.options().header())) {
				config.options().header(category.getDescription());
				changed = true;
			}

			for (ConfigEntry<?> entry : this.configEntries.get(category)) {
				if (!entry.getDescription().equals(config.getComment(entry.getPath()))) {
					config.setComment(entry.getPath(), entry.getDescription());
					changed = true;
				}
				if (!config.isSet(entry.getPath())) {
					config.path(entry.getPath()).set(entry.getDefaultValue());
					changed = true;
					entry.setValueAsObject(entry.getDefaultValue());
				} else
					entry.setValueAsObject(config.get(entry.getPath()));
			}

			if (changed)
				config.save();
		}
	}

	@Override
	public void dump() throws InvalidConfigurationException, IOException {
		for (ConfigCategory category : this.configEntries.keySet()) {
			YamlFile config = new YamlFile(this.path + category.getFileName());
			config.options().charset(StandardCharsets.UTF_8);
			config.createNewFile();
			config.setCommentFormat(YamlCommentFormat.PRETTY);
			config.options().header(category.getDescription());

			for (ConfigEntry<?> entry : this.configEntries.get(category)) {
				config.path(entry.getPath()).set(entry.getValue());
				config.setComment(entry.getPath(), entry.getDescription());
			}

			config.save();
		}
	}

	@Override
	public void delete() {
		for (ConfigCategory category : this.configEntries.keySet()) {
			File file = new File(this.path + category.getFileName());
			if (file.exists())
				file.delete();
		}
	}

	@Override
	public Multimap<ConfigCategory, ConfigEntry<?>> getConfigEntries() {
		return this.configEntries;
	}

	@Override
	public ConfigEntry<?> getEntry(ConfigCategory category, String path) {
		Collection<ConfigEntry<?>> entries = this.configEntries.get(category);
		return entries != null ? entries.stream().filter(entry -> entry.getPath().equals(path)).findAny().orElse(null) : null;
	}

	protected class ConfigEntryImpl<T> implements ConfigEntry<T> {

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
			this.description = description;

			AbstractConfig.this.addConfigEntry(this);
		}

		private void checkType(Class<?> type) throws ConfigException {
			if (this.defaultValue.getClass() != type && (this.defaultValue.getClass() != Long.class || type != Integer.class)
					&& (List.class.getClass().isAssignableFrom(this.defaultValue.getClass()) || List.class.getClass().isAssignableFrom(type)))
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
			this.checkType(value.getClass());
			this.value = (T) value;
		}
	}
}
