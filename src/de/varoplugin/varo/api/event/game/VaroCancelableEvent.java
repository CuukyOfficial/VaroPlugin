package de.varoplugin.varo.api.event.game;

import de.varoplugin.varo.game.Varo;
import org.bukkit.event.Cancellable;

public abstract class VaroCancelableEvent extends VaroGameEvent implements Cancellable {

    private boolean cancelled;

    public VaroCancelableEvent(Varo varo) {
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