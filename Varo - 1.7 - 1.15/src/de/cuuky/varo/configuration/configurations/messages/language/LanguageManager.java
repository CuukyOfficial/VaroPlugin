package de.cuuky.varo.configuration.configurations.messages.language;

import java.io.File;
import java.util.HashMap;

import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.messages.language.languages.DefaultLanguage;

public class LanguageManager {

	private String languagePath;
	private Language defaultLanguage;
	private HashMap<String, Language> languages;

	public LanguageManager(String languagesPath) {
		this.languagePath = languagesPath;
		this.languages = new HashMap<>();
	}

	protected String getMessage(String messagePath, String locale) {
		if(locale == null || !ConfigSetting.MAIN_LANGUAGE_ALLOW_OTHER.getValueAsBoolean())
			return defaultLanguage.getMessage(messagePath);
		else {
			Language language = languages.get(locale);
			String message = null;

			if(language == null)
				message = (language = defaultLanguage).getMessage(messagePath);
			else {
				message = language.getMessage(messagePath);

				if(message == null)
					message = (language = defaultLanguage).getMessage(messagePath);
			}

			if(message == null)
				message = languages.get("de_DE").getMessage(messagePath);

			return message;
		}
	}

	protected Language registerLanguage(String name) {
		return registerDefaultLanguage(name, null);
	}

	protected Language registerDefaultLanguage(String name, Class<? extends DefaultLanguage> clazz) {
		Language language = null;
		languages.put(name, language = new Language(name, this, clazz));

		return language;
	}
	
	protected void setDefaultLanguage(Language defaultLanguage) {
		this.defaultLanguage = defaultLanguage;
	}
	
	public void loadLanguages() {
		File file = new File(languagePath);
		for(File listFile : file.listFiles()) {
			if(!listFile.getName().endsWith(".yml") || languages.containsKey(listFile.getName().replace(".yml", "")))
				continue;

			registerLanguage(listFile.getName().replace(".yml", ""));
		}
	}

	public String getLanguagePath() {
		return this.languagePath;
	}

	public HashMap<String, Language> getLanguages() {
		return this.languages;
	}
}