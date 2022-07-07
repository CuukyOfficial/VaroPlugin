package de.varoplugin.varo.api.event.game.world.protectable;

import de.varoplugin.varo.game.world.protectable.Protectable;

public class VaroProtectableInitializedEvent extends VaroProtectableEvent {

    public VaroProtectableInitializedEvent(Protectable protectable) {
        super(protectable);
    }
}
