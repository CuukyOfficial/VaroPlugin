package de.cuuky.varo.api.game;

import org.bukkit.event.HandlerList;

import de.cuuky.varo.api.VaroEvent;
import de.cuuky.varo.game.VaroGame;
import de.cuuky.varo.game.WinnerCheck;

public class VaroEndEvent extends VaroEvent {
    
    private static final HandlerList handlers = new HandlerList();

	private final VaroGame game;
	private final WinnerCheck check;

	public VaroEndEvent(VaroGame game, WinnerCheck check) {
		this.game = game;
		this.check = check;
	}

	public VaroGame getGame() {
		return this.game;
	}

	public WinnerCheck getCheck() {
		return this.check;
	}

	@Override
    public HandlerList getHandlers() {
        return handlers;
    }
    
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
