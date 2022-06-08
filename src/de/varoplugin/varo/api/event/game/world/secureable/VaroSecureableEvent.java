package de.varoplugin.varo.api.event.game.world.secureable;

import de.varoplugin.varo.api.event.game.VaroGameCancelableEvent;
import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.world.protectable.VaroProtectable;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public abstract class VaroSecureableEvent extends VaroGameCancelableEvent {

    protected final VaroProtectable secureable;

    public VaroSecureableEvent(Varo varo, VaroProtectable secureable) {
        super(varo);

        this.secureable = secureable;
    }

    public VaroProtectable getSecureable() {
        return this.secureable;
    }
}