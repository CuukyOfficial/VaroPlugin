package de.varoplugin.varo.api.event.game.world.secureable;

import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.world.protectable.VaroProtectable;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class VaroSecureableRemoveEvent extends VaroSecureableEvent {

    public VaroSecureableRemoveEvent(Varo varo, VaroProtectable secureable) {
        super(varo, secureable);
    }
}