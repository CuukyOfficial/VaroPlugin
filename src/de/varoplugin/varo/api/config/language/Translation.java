package de.varoplugin.varo.api.config.language;

import org.bukkit.configuration.file.YamlConfiguration;

public interface Translation<T> {
	
	String path();

	T value();
	
	void loadValue(YamlConfiguration yaml);
	
	void saveValue(YamlConfiguration yaml);
}
