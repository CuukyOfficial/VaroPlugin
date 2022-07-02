package de.varoplugin.varo.api.event.game.world.protectable;

import de.varoplugin.varo.game.world.protectable.VaroProtectable;

public class VaroProtectableAddEvent extends VaroProtectableEvent {

    public VaroProtectableAddEvent(VaroProtectable protectable) {
        super(protectable);
    }
}