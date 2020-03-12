package de.cuuky.varo.configuration.placeholder.placeholder;

import java.util.ArrayList;
import java.util.HashMap;

import de.cuuky.varo.configuration.placeholder.MessagePlaceholder;
import de.cuuky.varo.entity.player.VaroPlayer;

public abstract class PlayerMessagePlaceholder extends MessagePlaceholder {

	private static ArrayList<PlayerMessagePlaceholder> playerPlaceholder;

	private HashMap<VaroPlayer, String> placeholderValues;
	private HashMap<VaroPlayer, Long> placeholderRefreshes;

	public PlayerMessagePlaceholder(String identifier, int refreshDelay, String description) {
		super(identifier, refreshDelay, description);

		this.placeholderValues = new HashMap<>();
		this.placeholderRefreshes = new HashMap<>();

		if(playerPlaceholder == null)
			playerPlaceholder = new ArrayList<>();

		playerPlaceholder.add(this);
	}

	private void checkRefresh(VaroPlayer player) {
		if(!shallRefresh(player))
			return;

		refreshValue(player);
	}

	private boolean shallRefresh(VaroPlayer player) {
		if(!this.placeholderRefreshes.containsKey(player))
			return true;

		return this.refreshDelay < 1 ? false : this.placeholderRefreshes.get(player) + this.refreshDelay <= System.currentTimeMillis();
	}

	private void refreshValue(VaroPlayer player) {
		this.placeholderValues.put(player, getValue(player));
		this.placeholderRefreshes.put(player, System.currentTimeMillis());
	}

	protected abstract String getValue(VaroPlayer player);

	public String replacePlaceholder(String message, VaroPlayer player) {
		checkRefresh(player);

		return message.replace(identifier, placeholderValues.get(player));
	}
	
	@Override
	public void clearValue() {
		placeholderValues.clear();
		placeholderRefreshes.clear();
	}

	public static ArrayList<PlayerMessagePlaceholder> getPlayerPlaceholder() {
		return playerPlaceholder;
	}
}