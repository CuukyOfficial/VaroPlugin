package de.varoplugin.varo.api.event.game.world.protectable;

import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.world.protectable.VaroProtectable;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class VaroProtectableRemoveEvent extends VaroProtectableEvent {

    public VaroProtectableRemoveEvent(Varo varo, VaroProtectable protectable) {
        super(varo, protectable);
    }
}