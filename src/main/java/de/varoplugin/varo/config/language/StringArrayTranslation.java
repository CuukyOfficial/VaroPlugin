package de.varoplugin.varo.config.language;

import java.util.Arrays;

import org.bukkit.configuration.file.YamlConfiguration;

public class StringArrayTranslation implements Translation<String[]> {
	
	private final String path;
	private String[] value;
	
	public StringArrayTranslation(String path, String... value) {
		this.path = path;
		this.value = value;
	}

	@Override
	public String path() {
		return this.path;
	}

	@Override
	public String[] value() {
		return this.value;
	}

	@Override
	public void loadValue(YamlConfiguration yaml) {
		this.value = yaml.getStringList(this.path).toArray(new String[0]);
	}

	@Override
	public void saveValue(YamlConfiguration yaml) {
		yaml.set(this.path, Arrays.asList(this.value));
	}

}
