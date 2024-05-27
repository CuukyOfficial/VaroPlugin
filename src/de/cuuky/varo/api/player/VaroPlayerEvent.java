package de.cuuky.varo.api.player;

import org.bukkit.event.HandlerList;

import de.cuuky.varo.api.VaroEvent;
import de.cuuky.varo.entity.player.VaroPlayer;

public class VaroPlayerEvent extends VaroEvent {
    
    private static final HandlerList handlers = new HandlerList();

    private final VaroPlayer player;

    public VaroPlayerEvent(VaroPlayer player) {
        this.player = player;
    }

    public VaroPlayer getPlayer() {
        return this.player;
    }
    
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
