package de.varoplugin.varo.bot;

import java.util.concurrent.TimeUnit;

public interface VaroBot {

	void connect();

	void disconnect(long duration, TimeUnit timeUnit);

	default void disconnect() {
		this.disconnect(0, null);
	}

}