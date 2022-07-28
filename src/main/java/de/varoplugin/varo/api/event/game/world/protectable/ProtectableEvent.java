package de.varoplugin.varo.api.event.game.world.protectable;

import de.varoplugin.varo.api.event.game.VaroCancelableEvent;
import de.varoplugin.varo.game.world.protectable.Protectable;

public abstract class ProtectableEvent extends VaroCancelableEvent {

    protected final Protectable protectable;

    public ProtectableEvent(Protectable protectable) {
        super(protectable.getVaro());

        this.protectable = protectable;
    }

    public Protectable getProtectable() {
        return this.protectable;
    }
}