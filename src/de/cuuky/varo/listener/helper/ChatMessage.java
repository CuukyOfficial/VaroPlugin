package de.cuuky.varo.listener.helper;

public class ChatMessage {

	private String message;
	private long timestamp;

	public ChatMessage(String message) {
		this.message = message;
		this.timestamp = System.currentTimeMillis();
	}

	public String getMessage() {
		return message;
	}

	public long getTimestamp() {
		return timestamp;
	}
}