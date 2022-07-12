package de.varoplugin.varo.api.config.language;

import org.bukkit.configuration.file.YamlConfiguration;

public class StringTranslation implements Translation<String> {
	
	private final String path;
	private String value;
	
	public StringTranslation(String path, String value) {
		this.path = path;
		this.value = value;
	}

	@Override
	public String path() {
		return this.path;
	}

	@Override
	public String value() {
		return this.value;
	}

	@Override
	public void loadValue(YamlConfiguration yaml) {
		this.value = yaml.getString(this.path);
	}

	@Override
	public void saveValue(YamlConfiguration yaml) {
		yaml.set(this.path, this.value);
	}
}
