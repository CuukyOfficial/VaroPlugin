package de.varoplugin.varo.event.game;

import de.varoplugin.varo.game.Varo;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public abstract class VaroGameEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final Varo varo;

    public VaroGameEvent(Varo varo) {
        this.varo = varo;
    }

    public Varo getGame() {
        return this.varo;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}