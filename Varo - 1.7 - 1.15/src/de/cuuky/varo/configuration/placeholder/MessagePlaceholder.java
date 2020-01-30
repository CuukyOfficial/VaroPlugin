package de.cuuky.varo.configuration.placeholder;

import java.util.ArrayList;

public abstract class MessagePlaceholder {

	private static ArrayList<MessagePlaceholder> placeholder;

	static {
		placeholder = new ArrayList<>();

		new MessagePlaceholderLoader();
	}

	protected String identifier;
	protected int refreshDelay;

	public MessagePlaceholder(String identifier, int refreshDelay) {
		this(identifier, refreshDelay, false);
	}

	public MessagePlaceholder(String identifier, int refreshDelay, boolean rawIdentifier) {
		if(rawIdentifier)
			this.identifier = identifier;
		else
			this.identifier = "%" + identifier + "%";
		
		this.refreshDelay = refreshDelay * 1000 - 100;

		placeholder.add(this);
	}

	public boolean containsPlaceholder(String message) {
		return message.contains(identifier);
	}

	public int getRefreshDelay() {
		return this.refreshDelay;
	}

	public static ArrayList<MessagePlaceholder> getPlaceholder() {
		return placeholder;
	}
}