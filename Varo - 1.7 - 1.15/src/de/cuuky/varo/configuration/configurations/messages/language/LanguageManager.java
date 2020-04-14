package de.cuuky.varo.configuration.configurations.messages.language;

import java.util.HashMap;

import org.bukkit.plugin.Plugin;

import de.cuuky.varo.configuration.configurations.messages.language.languages.LanguageMessage;

public class LanguageManager {

	private Plugin plugin;
	private Language defaultLanguage;
	private HashMap<String, Language> languages;

	public LanguageManager(Plugin plugin) {
		this.plugin = plugin;
		this.languages = new HashMap<>();
	}

	public String getMessage(int messageId, String locale) {
		if(locale == null)
			return defaultLanguage.getMessage(messageId);
		else {
			Language language = languages.get(locale);
			if(language == null) 
				language = defaultLanguage;
			
			return language.getMessage(messageId);
		}
	}

	@Deprecated
	/*
	 * @deprecated: Please use getMessage(int messageId, NetworkManager manager) instead
	 */
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

	public String getMessage(LanguageMessage message, String locale) {
		return getMessage(message.getMessageID(), locale);
	}

	public Language registerLanguage(String name, Class<? extends LanguageMessage> clazz, boolean defaultLanguage) {
		return registerLanguage(name, clazz, null, defaultLanguage);
	}

	public Language registerLanguage(String name, Class<? extends LanguageMessage> clazz, String languagePath, boolean defaultLanguage) {
		Language language = null;
		languages.put(name, language = new Language(name, clazz, languagePath == null ? "plugins/" + this.plugin.getName() + "/messages/" : languagePath));

		if(defaultLanguage) {
			this.defaultLanguage = language;
//			language.load();
		}

		return language;
	}
}