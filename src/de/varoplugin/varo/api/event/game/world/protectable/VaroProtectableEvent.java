package de.varoplugin.varo.api.event.game.world.protectable;

import de.varoplugin.varo.api.event.game.VaroGameCancelableEvent;
import de.varoplugin.varo.game.world.protectable.VaroProtectable;

public abstract class VaroProtectableEvent extends VaroGameCancelableEvent {

    protected final VaroProtectable protectable;

    public VaroProtectableEvent(VaroProtectable protectable) {
        super(protectable.getVaro());

        this.protectable = protectable;
    }

    public VaroProtectable getProtectable() {
        return this.protectable;
    }
}