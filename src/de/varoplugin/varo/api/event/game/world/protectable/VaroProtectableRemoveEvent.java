package de.varoplugin.varo.api.event.game.world.protectable;

import de.varoplugin.varo.game.world.protectable.VaroProtectable;

public class VaroProtectableRemoveEvent extends VaroProtectableEvent {

    public VaroProtectableRemoveEvent(VaroProtectable protectable) {
        super(protectable);
    }
}