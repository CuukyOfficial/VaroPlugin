package de.varoplugin.varo.api.config;

import java.io.IOException;

import org.simpleyaml.exceptions.InvalidConfigurationException;

import com.google.common.collect.Multimap;

public interface Config {

	void addConfigEntry(ConfigEntry<?> configEntry);

	void load() throws ConfigException, IOException;

	void dump() throws InvalidConfigurationException, IOException;

	void delete();

	Multimap<ConfigCategory, ConfigEntry<?>> getConfigEntries();

	ConfigEntry<?> getEntry(ConfigCategory category, String path);

}