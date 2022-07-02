package de.varoplugin.varo.config;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

import org.simpleyaml.configuration.comments.format.YamlCommentFormat;
import org.simpleyaml.configuration.file.YamlFile;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

import de.varoplugin.varo.api.config.Config;
import de.varoplugin.varo.api.config.ConfigCategory;
import de.varoplugin.varo.api.config.ConfigEntry;
import de.varoplugin.varo.api.config.ConfigException;

public class ConfigImpl implements Config {

	private final Multimap<ConfigCategory, ConfigEntry<?>> configEntries = LinkedHashMultimap.create();
	private final String path;

	public ConfigImpl(String path) {
		this.path = path;
	}

	@Override
	public void load() throws ConfigException {
		try {
			for (ConfigCategory category : this.configEntries.keySet()) {
				YamlFile config = new YamlFile(this.path + category.getFileName());
				config.options().charset(StandardCharsets.UTF_8);
				config.createOrLoad();
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
		} catch (IOException e) {
			throw new ConfigException(e);
		}
	}

	@Override
	public void dump() throws ConfigException {
		try {
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
		} catch (IOException e) {
			throw new ConfigException(e);
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
	public void addConfigEntry(ConfigEntry<?> configEntry) {
		this.configEntries.put(configEntry.getCategory(), configEntry);
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

}
