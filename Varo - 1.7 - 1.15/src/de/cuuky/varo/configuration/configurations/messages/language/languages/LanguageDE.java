package de.cuuky.varo.configuration.configurations.messages.language.languages;

public enum LanguageDE implements LanguageMessage {

	TEST_MESSAGE(0, "testMessage", "Das hier ist eine deutsche Testnachricht!");

	private int messageId;
	private String path, message;
	
	private LanguageDE(int messageId, String path, String message) {
		this.messageId = messageId;
		this.path = path;
		this.message = message;
	}
	
	@Override
	public int getMessageID() {
		return messageId;
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