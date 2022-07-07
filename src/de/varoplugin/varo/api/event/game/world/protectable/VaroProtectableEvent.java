package de.varoplugin.varo.api.event.game.world.protectable;

import de.varoplugin.varo.api.event.game.VaroGameCancelableEvent;
import de.varoplugin.varo.game.world.protectable.Protectable;

public abstract class VaroProtectableEvent extends VaroGameCancelableEvent {

    protected final Protectable protectable;

    public VaroProtectableEvent(Protectable protectable) {
        super(protectable.getVaro());

        this.protectable = protectable;
    }

    public Protectable getProtectable() {
        return this.protectable;
    }
}