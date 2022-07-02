package de.varoplugin.varo.api.event.game.world.protectable;

import de.varoplugin.varo.game.world.protectable.VaroProtectable;

public class VaroProtectableInitializedEvent extends VaroProtectableEvent {

    public VaroProtectableInitializedEvent(VaroProtectable protectable) {
        super(protectable);
    }
}
