package de.varoplugin.varo.api.player.strike;

import org.bukkit.event.HandlerList;

import de.varoplugin.varo.api.player.VaroPlayerEvent;
import de.varoplugin.varo.player.stats.stat.Strike;

public class PlayerStrikeRemoveEvent extends VaroPlayerEvent {
    
    private static final HandlerList handlers = new HandlerList();

	private final Strike strike;

	public PlayerStrikeRemoveEvent(Strike strike) {
		super(strike.getStriked());

		this.strike = strike;
	}

	public Strike getStrike() {
		return this.strike;
	}
	
	@Override
    public HandlerList getHandlers() {
        return handlers;
    }
    
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
