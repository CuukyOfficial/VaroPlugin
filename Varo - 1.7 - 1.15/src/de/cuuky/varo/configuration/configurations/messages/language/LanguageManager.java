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

	public void loadLanguages() {
		File file = new File(languagePath);
		for(File listFile : file.listFiles()) {
			if(!listFile.getName().endsWith(".yml") || languages.containsKey(listFile.getName().replace(".yml", "")))
				continue;

			registerLanguage(listFile.getName().replace(".yml", ""));
		}
	}

	public void setDefaultLanguage(Language defaultLanguage) {
		this.defaultLanguage = defaultLanguage;
	}

	public String getMessage(String messagePath, String locale) {
		if(locale == null)
			return defaultLanguage.getMessage(messagePath);
		else {
			Language language = languages.get(locale);
			String message = null;
			
			if(language == null || !ConfigSetting.MAIN_LANGUAGE_ALLOW_OTHER.getValueAsBoolean())
				message = (language = defaultLanguage).getMessage(messagePath);
			else {
				message = language.getMessage(messagePath);
				
				if(message == null)
					(language = defaultLanguage).getMessage(messagePath);
			}
			
			if(message == null)
				throw new NullPointerException("Couldn't find message for '" + messagePath + "' in language file '" + language.getName() + "'");

			return message;
		}
	}

	public Language registerLanguage(String name) {
		return registerDefaultLanguage(name, null);
	}

	public Language registerDefaultLanguage(String name, Class<? extends DefaultLanguage> clazz) {
		Language language = null;
		languages.put(name, language = new Language(name, this, clazz));

		return language;
	}

	public String getLanguagePath() {
		return this.languagePath;
	}

	public HashMap<String, Language> getLanguages() {
		return this.languages;
	}
}