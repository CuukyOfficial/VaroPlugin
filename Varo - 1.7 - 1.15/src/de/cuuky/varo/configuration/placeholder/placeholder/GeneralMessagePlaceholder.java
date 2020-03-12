package de.cuuky.varo.configuration.placeholder.placeholder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.cuuky.varo.configuration.placeholder.MessagePlaceholder;

public abstract class GeneralMessagePlaceholder extends MessagePlaceholder {

	private static ArrayList<GeneralMessagePlaceholder> generalPlaceholder;
	
	private static long lastDateRefreshTime;
	private static String[] lastDateRefresh;

	private String value;
	protected long lastRefresh;

	public GeneralMessagePlaceholder(String identifier, int refreshDelay, String description) {
		this(identifier, refreshDelay, false, description);
	}

	public GeneralMessagePlaceholder(String identifier, int refreshDelay, boolean rawIdentifier, String description) {
		super(identifier, refreshDelay, rawIdentifier, description);
		
		if(generalPlaceholder == null)
			generalPlaceholder = new ArrayList<>();
		
		this.lastRefresh = 0;

		generalPlaceholder.add(this);
	}
	
	private void checkRefresh() {
		if(!shallRefresh()) 
			return;
		
		refreshValue();
	}
	
	private void refreshValue() {
		this.value = getValue();
		this.lastRefresh = System.currentTimeMillis();
	}
	
	private boolean shallRefresh() {
		return this.refreshDelay < 1 && this.value != null ? false : this.lastRefresh + (this.refreshDelay) <= System.currentTimeMillis();
	}

	protected abstract String getValue();
	
	public String replacePlaceholder(String message) {
		checkRefresh();
		
		return message.replace(this.identifier, this.value);
	}
	
	@Override
	public void clearValue() {
		this.value = null;
		this.lastRefresh = 0;
	}
	
	protected static String getLastDateRefresh(int index) {
		if(lastDateRefresh == null || lastDateRefreshTime + 1000 <= System.currentTimeMillis()) {
			lastDateRefreshTime = System.currentTimeMillis();
			lastDateRefresh = new SimpleDateFormat("yyyy,MM,dd,HH,mm,ss").format(new Date()).split(",");
		}
		
		return lastDateRefresh[index];
	}
	
	public static ArrayList<GeneralMessagePlaceholder> getGeneralPlaceholder() {
		return generalPlaceholder;
	}
}