package de.varoplugin.varo.api.event.game.world.secureable;

import de.varoplugin.varo.api.event.game.VaroGameCancelableEvent;
import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.world.secureable.VaroSecureable;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public abstract class VaroSecureableEvent extends VaroGameCancelableEvent {

    protected final VaroSecureable secureable;

    public VaroSecureableEvent(Varo varo, VaroSecureable secureable) {
        super(varo);

        this.secureable = secureable;
    }

    public VaroSecureable getSecureable() {
        return this.secureable;
    }
}