package de.cuuky.varo.configuration.placeholder.placeholder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import de.cuuky.varo.configuration.placeholder.MessagePlaceholder;

public abstract class GeneralMessagePlaceholder extends MessagePlaceholder {

	private static ArrayList<GeneralMessagePlaceholder> generalPlaceholder;
	private static HashMap<String, ArrayList<GeneralMessagePlaceholder>> cachedRequests;

	static {
		cachedRequests = new HashMap<>();
	}

	private static long lastDateRefreshTime;
	private static String[] lastDateRefresh;

	private String value;
	protected long lastRefresh;

	public GeneralMessagePlaceholder(String identifier, int refreshDelay, String description) {
		this(identifier, refreshDelay, false, description);
	}

	public GeneralMessagePlaceholder(String identifier, int refreshDelay, boolean rawIdentifier, String description) {
		super(identifier, refreshDelay, rawIdentifier, description);

		if (generalPlaceholder == null)
			generalPlaceholder = new ArrayList<>();

		this.lastRefresh = 0;

		generalPlaceholder.add(this);
	}

	private void checkRefresh() {
		if (!shallRefresh(this.lastRefresh))
			return;

		refreshValue();
	}

	private void refreshValue() {
		this.value = getValue();
		this.lastRefresh = System.currentTimeMillis();
	}

	protected abstract String getValue();

	public String replacePlaceholder(String message) {
		checkRefresh();

		return message.replace(this.identifier, this.value != null ? this.value : "");
	}

	@Override
	public void clearValue() {
		this.value = null;
		this.lastRefresh = 0;
	}

	protected static String getLastDateRefresh(int index) {
		if (lastDateRefresh == null || lastDateRefreshTime + 900 <= System.currentTimeMillis()) {
			lastDateRefreshTime = System.currentTimeMillis();
			lastDateRefresh = new SimpleDateFormat("yyyy,MM,dd,HH,mm,ss").format(new Date()).split(",");
		}

		return lastDateRefresh[index];
	}

	private static Object[] replaceByList(String value, ArrayList<GeneralMessagePlaceholder> list) {
		ArrayList<GeneralMessagePlaceholder> cached = new ArrayList<>();
		for (GeneralMessagePlaceholder pmp : list)
			if (pmp.containsPlaceholder(value)) {
				value = pmp.replacePlaceholder(value);
				cached.add(pmp);
			}

		return new Object[] { value, cached };
	}

	public static String replacePlaceholders(String value) {
		if (cachedRequests.get(value) != null)
			return (String) replaceByList(value, cachedRequests.get(value))[0];
		else {
			Object[] result = replaceByList(value, generalPlaceholder);
			cachedRequests.put(value, (ArrayList<GeneralMessagePlaceholder>) result[1]);
			return (String) result[0];
		}
	}

	public static void clearCache() {
		cachedRequests.clear();
	}

	public static ArrayList<GeneralMessagePlaceholder> getGeneralPlaceholderMatching() {
		return generalPlaceholder;
	}
}