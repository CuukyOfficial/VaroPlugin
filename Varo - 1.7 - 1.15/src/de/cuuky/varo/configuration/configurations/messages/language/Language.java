package de.cuuky.varo.configuration.configurations.messages.language;

import java.io.File;
import java.util.HashMap;

import org.bukkit.configuration.file.YamlConfiguration;

public class Language {

	private String name, path;
	
	private File file;
	private YamlConfiguration configuration;

	private HashMap<String, String> messages;

	public Language(String name, String path) {
		this.name = name;
		this.path = path;
		this.messages = new HashMap<>();
		
		load();
	}

	public void load() {
		this.file = new File(path, name + ".yml");
		this.configuration = YamlConfiguration.loadConfiguration(this.file);
		
		for(String path : this.configuration.getKeys(true)) {
			if(this.configuration.isConfigurationSection(path))
				return;
			
			messages.put(path, this.configuration.getString(path));
		}
	}

	public String getMessage(String path) {
		String message = messages.get(path);
		return message != null ? message : null;
	}
}