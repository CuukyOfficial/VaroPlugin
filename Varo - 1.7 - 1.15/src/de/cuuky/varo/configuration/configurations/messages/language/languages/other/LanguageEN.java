package de.cuuky.varo.configuration.configurations.messages.language.languages.other;

import de.cuuky.varo.configuration.configurations.messages.language.languages.LoadableMessage;

public enum LanguageEN implements LoadableMessage {

	;
	
	private String path, message;
	
	private LanguageEN(String path, String message) {
		this.path = path;
		this.message = message;
	}

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public String getDefaultMessage() {
		return message;
	}
}