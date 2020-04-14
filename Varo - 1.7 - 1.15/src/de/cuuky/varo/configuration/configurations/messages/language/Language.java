package de.cuuky.varo.configuration.configurations.messages.language;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.configuration.file.YamlConfiguration;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.messages.language.languages.LanguageMessage;

public class Language {

	private String name, languagePath;
	private boolean loaded;

	private File file;
	private YamlConfiguration configuration;

	private Class<? extends LanguageMessage> clazz;
	private HashMap<Integer, LanguageMessage> messages;

	public Language(String name, Class<? extends LanguageMessage> clazz, String languagePath) {
		this.name = name;
		this.clazz = clazz;
		this.languagePath = languagePath;
		this.messages = new HashMap<>();
		
		load();
	}

	private void saveConfiguration() {
		try {
			this.configuration.save(this.file);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void load() {
		LanguageMessage[] messages = null;
		try {
			messages = (LanguageMessage[]) clazz.getMethod("values").invoke(null);
		} catch(Exception e) {
			System.err.println(Main.getConsolePrefix() + clazz.getName() + " is no valid enum class!");
			System.exit(0);
			return;
		}

		this.file = new File(languagePath, this.name + ".yml");
		this.configuration = YamlConfiguration.loadConfiguration(this.file);

		boolean save = file.exists();
		ArrayList<String> pathNames = new ArrayList<>();
		for(LanguageMessage message : messages) {
			if(!configuration.contains(message.getPath())) {
				this.configuration.set(message.getPath(), message.getMessage());
				save = true;
			}

			pathNames.add(message.getPath());
			message.setMessage(this.configuration.getString(message.getPath()));
			this.messages.put(message.getMessageID(), message);
		}

		for(String path : configuration.getKeys(true)) {
			if(pathNames.contains(path) || configuration.isConfigurationSection(path))
				continue;

			System.out.println(Main.getConsolePrefix() + "Removed " + path + " in " + file.getAbsolutePath() + " because it was removed from the plugin");
			configuration.set(path, null);
			save = true;
		}

		if(save)
			saveConfiguration();

		loaded = true;
	}

	public String getMessage(int id) {
		if(!loaded)
			load();

		LanguageMessage message = messages.get(id);
		return message != null ? message.getMessage() : null;
	}

	public String getMessage(String path) {
		if(!loaded)
			load();

		for(LanguageMessage message : messages.values())
			if(message.getPath().equals(path))
				return message.getMessage();

		return null;
	}
}