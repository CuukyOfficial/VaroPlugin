package de.cuuky.varo.configuration.configurations.messages.language;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import de.cuuky.varo.configuration.configurations.messages.language.languages.LoadableMessage;

public class LanguageManager {

	private String languagePath;
	private Language defaultLanguage;
	private HashMap<String, Language> languages;
	private HashMap<String, String> defaultMessages;

	public LanguageManager(String languagesPath) {
		this.languagePath = languagesPath;
		this.languages = new HashMap<>();
		this.defaultMessages = new HashMap<>();
	}

	protected Language registerLanguage(String name) {
		return registerLoadableLanguage(name, null);
	}

	protected Language registerLoadableLanguage(String name, Class<? extends LoadableMessage> clazz) {
		Language language = null;
		languages.put(name, language = new Language(name, this, clazz));

		return language;
	}

	protected void setDefaultLanguage(Language defaultLanguage) {
		this.defaultLanguage = defaultLanguage;
		this.defaultMessages = getValues(defaultLanguage.getClazz());

		this.defaultLanguage.load();
		for(Language lang : this.languages.values())
			if(!lang.isLoaded() && !lang.getFile().exists())
				lang.load();
	}

	protected HashMap<String, String> getValues(Class<? extends LoadableMessage> clazz) {
		HashMap<String, String> values = new HashMap<>();
		LoadableMessage[] messages = null;

		try {
			messages = (LoadableMessage[]) clazz.getMethod("values").invoke(null);
		} catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}

		for(LoadableMessage lm : messages)
			values.put(lm.getPath(), lm.getDefaultMessage());

		return values;
	}

	public void loadLanguages() {
		File file = new File(languagePath);
		if(!file.isDirectory())
			file.mkdir();

		for(File listFile : file.listFiles()) {
			if(!listFile.getName().endsWith(".yml") || languages.containsKey(listFile.getName().replace(".yml", "")))
				continue;

			registerLanguage(listFile.getName().replace(".yml", ""));
		}
	}

	public String getLanguagePath() {
		return this.languagePath;
	}

	public Language getDefaultLanguage() {
		return this.defaultLanguage;
	}

	public HashMap<String, String> getDefaultMessages() {
		return this.defaultMessages;
	}

	public HashMap<String, Language> getLanguages() {
		return this.languages;
	}
}