package de.cuuky.varo.configuration.placeholder.placeholder;

import java.util.ArrayList;
import java.util.HashMap;

import de.cuuky.varo.configuration.placeholder.MessagePlaceholder;
import de.cuuky.varo.entity.player.VaroPlayer;

public abstract class PlayerMessagePlaceholder extends MessagePlaceholder {
	
	private static ArrayList<PlayerMessagePlaceholder> playerPlaceholder = new ArrayList<>();
	
	private HashMap<VaroPlayer, String> placeholderValues;

	public PlayerMessagePlaceholder(String identifier, int refreshDelay) {
		super(identifier, refreshDelay);
		
		this.placeholderValues = new HashMap<>();
		
		if(playerPlaceholder == null)
			playerPlaceholder = new ArrayList<>();
		
		playerPlaceholder.add(this);
	}

	@Override
	protected void refreshValues() {
		for(VaroPlayer vp : placeholderValues.keySet())
			placeholderValues.put(vp, getValue(vp));
	}
	
	protected abstract String getValue(VaroPlayer player);
	
	public String replacePlaceholder(String message, VaroPlayer player) {
		if(!placeholderValues.containsKey(player))
			placeholderValues.put(player, getValue(player));
		
		return message.replace(identifier, placeholderValues.get(player)); 
	}
	
	public static ArrayList<PlayerMessagePlaceholder> getPlayerPlaceholder() {
		return playerPlaceholder;
	}
}