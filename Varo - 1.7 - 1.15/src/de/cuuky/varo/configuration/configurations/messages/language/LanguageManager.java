package de.cuuky.varo.configuration.configurations.messages.language;

import java.io.File;
import java.util.HashMap;

public class LanguageManager {

	private String languagePath;
	private Language defaultLanguage;
	private HashMap<String, Language> languages;

	public LanguageManager(String languagesPath) {
		this.languagePath = languagesPath;
		this.languages = new HashMap<>();
		
		loadLanguages();
	}
	
	private void loadLanguages() {
		File file = new File(languagePath);
		for(File listFile : file.listFiles()) {
			if(!listFile.getName().endsWith(".yml"))
				continue;
			
			registerLanguage(listFile.getName().replace(".yml", ""), false);
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
			if(language == null) 
				language = defaultLanguage;
			
			return language.getMessage(messagePath);
		}
	}

	public Language registerLanguage(String name, boolean defaultLanguage) {
		Language language = null;
		languages.put(name, language = new Language(name, this.languagePath));

		if(defaultLanguage) {
			this.defaultLanguage = language;
//			language.load();
		}

		return language;
	}
	
	public HashMap<String, Language> getLanguages() {
		return this.languages;
	}
}