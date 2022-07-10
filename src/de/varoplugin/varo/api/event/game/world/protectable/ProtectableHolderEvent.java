package de.varoplugin.varo.api.event.game.world.protectable;

import de.varoplugin.varo.game.world.protectable.Protectable;
import de.varoplugin.varo.game.world.protectable.ProtectableHolder;

public class ProtectableHolderEvent extends ProtectableEvent {

    private final ProtectableHolder holder;

    public ProtectableHolderEvent(ProtectableHolder holder, Protectable protectable) {
        super(protectable);
        this.holder = holder;
    }

    public ProtectableHolder getHolder() {
        return this.holder;
    }
}
