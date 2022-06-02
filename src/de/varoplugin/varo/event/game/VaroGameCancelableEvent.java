package de.varoplugin.varo.event.game;

import de.varoplugin.varo.game.Varo;
import org.bukkit.event.Cancellable;

public abstract class VaroGameCancelableEvent extends VaroGameEvent implements Cancellable {

    private boolean cancelled;

    public VaroGameCancelableEvent(Varo varo) {
        super(varo);
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}