package de.cuuky.varo.configuration.placeholder;

import java.util.ArrayList;

import de.cuuky.varo.configuration.placeholder.placeholder.GeneralMessagePlaceholder;
import de.cuuky.varo.configuration.placeholder.placeholder.PlayerMessagePlaceholder;

public abstract class MessagePlaceholder {

	private static final int TICK_TOLERANCE = 20;

	private static ArrayList<MessagePlaceholder> placeholders;

	static {
		placeholders = new ArrayList<>();

		new MessagePlaceholderLoader();
	}

	protected String identifier, description;
	protected int defaultRefresh, refreshDelay;

	public MessagePlaceholder(String identifier, int refreshDelay, String description) {
		this(identifier, refreshDelay, false, description);
	}

	public MessagePlaceholder(String identifier, int refreshDelay, boolean rawIdentifier, String description) {
		if(rawIdentifier)
			this.identifier = identifier;
		else
			this.identifier = "%" + identifier + "%";

		this.description = description;
		this.defaultRefresh = refreshDelay;
		this.refreshDelay = (int) (refreshDelay * 1000);

		placeholders.add(this);
	}

	protected boolean shallRefresh(long last) {
		if(last < 1)
			return true;

		return (System.currentTimeMillis() - (last + this.refreshDelay)) - TICK_TOLERANCE < 1 ? true : false;
	}

	public boolean containsPlaceholder(String message) {
		return message.contains(identifier);
	}

	public String getIdentifier() {
		return this.identifier;
	}

	public String getDescription() {
		return this.description;
	}

	public int getDefaultRefresh() {
		return this.defaultRefresh;
	}

	public abstract void clearValue();

	public static void clearPlaceholder() {
		GeneralMessagePlaceholder.clearCache();
		PlayerMessagePlaceholder.clearCache();

		for(MessagePlaceholder placeholder : placeholders)
			placeholder.clearValue();
	}

	public static ArrayList<MessagePlaceholder> getPlaceholders() {
		return placeholders;
	}
}