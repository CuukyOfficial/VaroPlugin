package de.cuuky.varo.configuration.placeholder.placeholder;

import java.util.ArrayList;
import java.util.HashMap;

import de.cuuky.varo.configuration.placeholder.MessagePlaceholder;
import de.cuuky.varo.entity.player.VaroPlayer;

public abstract class PlayerMessagePlaceholder extends MessagePlaceholder {

	private static ArrayList<PlayerMessagePlaceholder> playerPlaceholder;
	private static HashMap<String, ArrayList<PlayerMessagePlaceholder>> cachedRequests;

	static {
		cachedRequests = new HashMap<>();
	}

	private HashMap<VaroPlayer, String> placeholderValues;
	private HashMap<VaroPlayer, Long> placeholderRefreshes;

	public PlayerMessagePlaceholder(String identifier, int refreshDelay, String description) {
		super(identifier, refreshDelay, description);

		this.placeholderValues = new HashMap<>();
		this.placeholderRefreshes = new HashMap<>();

		if (playerPlaceholder == null)
			playerPlaceholder = new ArrayList<>();

		playerPlaceholder.add(this);
	}

	private void checkRefresh(VaroPlayer player) {
		if (!shallRefresh(player))
			return;

		refreshValue(player);
	}

	private boolean shallRefresh(VaroPlayer player) {
		if (!this.placeholderRefreshes.containsKey(player))
			return true;

		return super.shallRefresh(this.placeholderRefreshes.get(player));
	}

	private void refreshValue(VaroPlayer player) {
		this.placeholderValues.put(player, getValue(player));
		this.placeholderRefreshes.put(player, System.currentTimeMillis());
	}

	protected abstract String getValue(VaroPlayer player);

	public String replacePlaceholder(String message, VaroPlayer player) {
		checkRefresh(player);
		
		String value = placeholderValues.get(player);
		return message.replace(identifier, value != null ? value : "");
	}

	@Override
	public void clearValue() {
		placeholderValues.clear();
		placeholderRefreshes.clear();
	}

	private static Object[] replaceByList(String value, VaroPlayer vp, ArrayList<PlayerMessagePlaceholder> list) {
		ArrayList<PlayerMessagePlaceholder> cached = new ArrayList<>();
		for (PlayerMessagePlaceholder pmp : list)
			if (pmp.containsPlaceholder(value)) {
				value = pmp.replacePlaceholder(value, vp);
				cached.add(pmp);
			}

		return new Object[] { value, cached };
	}

	public static String replacePlaceholders(String value, VaroPlayer vp) {
		if (cachedRequests.get(value) != null)
			return (String) replaceByList(value, vp, cachedRequests.get(value))[0];
		else {
			Object[] result = replaceByList(value, vp, playerPlaceholder);
			cachedRequests.put(value, (ArrayList<PlayerMessagePlaceholder>) result[1]);
			return (String) result[0];
		}
	}

	public static void clearCache() {
		cachedRequests.clear();
	}

	public static ArrayList<PlayerMessagePlaceholder> getPlayerPlaceholder() {
		return playerPlaceholder;
	}
}