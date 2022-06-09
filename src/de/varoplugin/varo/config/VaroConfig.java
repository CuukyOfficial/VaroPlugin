package de.varoplugin.varo.config;

import static de.varoplugin.varo.config.VaroConfigCategory.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.simpleyaml.configuration.comments.format.YamlCommentFormat;
import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

import de.varoplugin.varo.api.config.ConfigCategory;
import de.varoplugin.varo.api.config.ConfigEntry;
import de.varoplugin.varo.api.config.ConfigException;

public class VaroConfig {

	private final Multimap<ConfigCategory, ConfigEntry<?>> configEntries = LinkedHashMultimap.create();

	// These fields do not follow java conventions to improve readability
	public final ConfigEntry<Boolean> offlinemode = new VaroConfigEntry<>(MAIN, "offlinemode", false, "Whether the server is running in offline mode");
	
	public final ConfigEntry<Boolean> scoreboard_enabled = new VaroConfigEntry<>(SCOREBOARD, "scoreboard.enabled", true, "Whether the scoreboard should be enabled (Players may still be able to hide their scoreboard)");
	public final ConfigEntry<Integer> scoreboard_title_delay = new VaroConfigEntry<>(SCOREBOARD, "scoreboard.title.updatedelay", 100, "The update interval of the title animation");
	public final ConfigEntry<Integer> scoreboard_content_delay = new VaroConfigEntry<>(SCOREBOARD, "scoreboard.content.updatedelay", 100, "The update interval of the animation");
	
	private final String path;

	public VaroConfig(String path) {
		this.path = path;
	}

	public void addConfigEntry(ConfigEntry<?> configEntry) {
		this.configEntries.put(configEntry.getCategory(), configEntry);
	}

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
				if (config.get(entry.getPath()) == null) {
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

	public void delete() {
		for (ConfigCategory category : this.configEntries.keySet()) {
			File file = new File(this.path + category.getFileName());
			if (file.exists())
				file.delete();
		}
	}

	public Multimap<ConfigCategory, ConfigEntry<?>> getConfigEntries() {
		return this.configEntries;
	}

	private class VaroConfigEntry<T> implements ConfigEntry<T> {

		private final ConfigCategory category;
		private final String path;
		private final T defaultValue;
		private final String description;
		private T value;

		public VaroConfigEntry(ConfigCategory category, String path, T defaultValue, String description) {
			this.category = category;
			this.path = path;
			this.defaultValue = defaultValue;
			this.description = description;

			if (defaultValue == null)
				throw new Error("Default value of " + path + " is null");

			VaroConfig.this.addConfigEntry(this);
		}

		private void checkType(Class<?> type) throws ConfigException {
			if (this.defaultValue.getClass() != type && this.defaultValue.getClass() != Long.class && type != Integer.class)
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

		@Override
		public void setValueAsObject(Object value) throws ConfigException {
			this.checkType(value.getClass());
			this.value = (T) value;
		}
	}
}
