package de.cuuky.varo.api.game;

import org.bukkit.event.HandlerList;

import de.cuuky.varo.api.VaroEvent;
import de.cuuky.varo.game.VaroGame;

public class VaroStartEvent extends VaroEvent {
    
    private static final HandlerList handlers = new HandlerList();

	private final VaroGame game;

	public VaroStartEvent(VaroGame game) {
		this.game = game;
	}

	public VaroGame getGame() {
		return this.game;
	}
	
	@Override
    public HandlerList getHandlers() {
        return handlers;
    }
    
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
