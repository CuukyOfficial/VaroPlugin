package de.varoplugin.varo.api.player;

import org.bukkit.event.HandlerList;

import de.varoplugin.varo.player.VaroPlayer;
import de.varoplugin.varo.player.stats.stat.PlayerState;

public class PlayerStateChangeEvent extends VaroPlayerEvent {
    
    private static final HandlerList handlers = new HandlerList();

	private final PlayerState state;

	public PlayerStateChangeEvent(VaroPlayer player, PlayerState state) {
		super(player);

		this.state = state;
	}

	public PlayerState getState() {
		return this.state;
	}
	
	@Override
    public HandlerList getHandlers() {
        return handlers;
    }
    
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
