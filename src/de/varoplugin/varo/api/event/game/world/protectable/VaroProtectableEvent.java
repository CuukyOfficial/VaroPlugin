package de.varoplugin.varo.api.event.game.world.protectable;

import de.varoplugin.varo.api.event.game.VaroGameCancelableEvent;
import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.world.protectable.VaroProtectable;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public abstract class VaroProtectableEvent extends VaroGameCancelableEvent {

    protected final VaroProtectable protectable;

    public VaroProtectableEvent(Varo varo, VaroProtectable protectable) {
        super(varo);

        this.protectable = protectable;
    }

    public VaroProtectable getProtectable() {
        return this.protectable;
    }
}