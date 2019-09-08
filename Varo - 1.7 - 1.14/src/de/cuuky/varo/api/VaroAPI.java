package de.cuuky.varo.api;

import de.cuuky.varo.api.event.EventManager;

public class VaroAPI {

	private static EventManager eventManager;

	static {
		eventManager = new EventManager();
	}

	public static EventManager getEventManager() {
		return eventManager;
	}
}
