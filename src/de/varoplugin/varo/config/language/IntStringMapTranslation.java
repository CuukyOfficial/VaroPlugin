package de.varoplugin.varo.config.language;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;

public class IntStringMapTranslation implements Translation<Map<Integer, String>> {

	private final String path;
	private Map<Integer, String> value;

	public IntStringMapTranslation(String path, Map<Integer, String> value) {
		this.path = path;
		this.value = value;
	}

	@Override
	public String path() {
		return this.path;
	}

	@Override
	public Map<Integer, String> value() {
		return this.value;
	}

	@Override
	public void loadValue(YamlConfiguration yaml) {
		Map<Integer, String> map = new HashMap<>();
		yaml.getConfigurationSection(this.path).getValues(false).entrySet().forEach(entry -> map.put(Integer.parseInt(entry.getKey()), entry.getValue().toString()));
		this.value = Collections.unmodifiableMap(map);
	}

	@Override
	public void saveValue(YamlConfiguration yaml) {
		yaml.createSection(this.path, this.value);
	}

}
