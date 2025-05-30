package de.varoplugin.varo.logger;

import com.google.gson.annotations.Expose;

public class LogElement {

	@Expose
	private long timestamp;

	public LogElement(long timestamp) {
		this.timestamp = timestamp;
	}

	public long getTimestamp() {
		return timestamp;
	}
}
