package de.cuuky.varo.configuration.placeholder.placeholder;

import java.util.ArrayList;

import de.cuuky.varo.configuration.placeholder.MessagePlaceholder;

public abstract class GeneralMessagePlaceholder extends MessagePlaceholder {

	private static ArrayList<GeneralMessagePlaceholder> generalPlaceholder;

	private String value;
	protected long lastRefresh;

	public GeneralMessagePlaceholder(String identifier, int refreshDelay) {
		this(identifier, refreshDelay, false);
	}

	public GeneralMessagePlaceholder(String identifier, int refreshDelay, boolean rawIdentifier) {
		super(identifier, refreshDelay, rawIdentifier);
		
		if(generalPlaceholder == null)
			generalPlaceholder = new ArrayList<>();
		
		this.lastRefresh = 0;

		generalPlaceholder.add(this);
	}
	
	private void checkRefresh() {
		if(!shallRefresh()) 
			return;
		
		refreshValues();
	}
	
	private boolean shallRefresh() {
		return this.refreshDelay < 1 && this.value != null ? false : this.lastRefresh + (this.refreshDelay) <= System.currentTimeMillis();
	}
	
	private void refreshValues() {
		this.value = getValue();
		this.lastRefresh = System.currentTimeMillis();
	}

	protected abstract String getValue();
	
	public String replacePlaceholder(String message) {
		checkRefresh();
		
		return message.replace(this.identifier, this.value);
	}
	
	public static ArrayList<GeneralMessagePlaceholder> getGeneralPlaceholder() {
		return generalPlaceholder;
	}
}