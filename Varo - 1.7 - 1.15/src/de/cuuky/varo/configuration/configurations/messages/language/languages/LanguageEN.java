package de.cuuky.varo.configuration.configurations.messages.language.languages;

public enum LanguageEN implements DefaultLanguage {

	TEST_MESSAGE("test.message", "Heello, hov ar u");
	
	private String path, message;
	
	private LanguageEN(String path, String message) {
		this.path = path;
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}
	
	@Override
	public void setMessage(String message) {
		this.message = message;	
	}

	@Override
	public String getPath() {
		return path;
	}
}