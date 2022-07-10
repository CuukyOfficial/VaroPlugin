package de.varoplugin.varo.api.event.game.world.protectable;

import de.varoplugin.varo.game.world.protectable.Protectable;

public class ProtectableInitializedEvent extends ProtectableEvent {

    public ProtectableInitializedEvent(Protectable protectable) {
        super(protectable);
    }
}
