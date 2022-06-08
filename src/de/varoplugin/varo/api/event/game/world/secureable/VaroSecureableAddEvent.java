package de.varoplugin.varo.api.event.game.world.secureable;

import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.world.protectable.VaroProtectable;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class VaroSecureableAddEvent extends VaroSecureableEvent {

    public VaroSecureableAddEvent(Varo varo, VaroProtectable secureable) {
        super(varo, secureable);
    }
}