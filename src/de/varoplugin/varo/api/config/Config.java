package de.varoplugin.varo.api.config;

import com.google.common.collect.Multimap;

public interface Config {

	void load() throws ConfigException;

	void dump() throws ConfigException;

	void delete() throws ConfigException;
	
	void addConfigEntry(ConfigEntry<?> configEntry);

	Multimap<ConfigCategory, ConfigEntry<?>> getConfigEntries();

	ConfigEntry<?> getEntry(ConfigCategory category, String path);

}