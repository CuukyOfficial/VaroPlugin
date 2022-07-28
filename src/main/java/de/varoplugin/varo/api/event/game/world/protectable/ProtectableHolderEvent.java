package de.varoplugin.varo.api.event.game.world.protectable;

import de.varoplugin.varo.game.world.protectable.Protectable;
import de.varoplugin.varo.game.world.protectable.ProtectableOwner;

public class ProtectableHolderEvent extends ProtectableEvent {

    private final ProtectableOwner holder;

    public ProtectableHolderEvent(ProtectableOwner holder, Protectable protectable) {
        super(protectable);
        this.holder = holder;
    }

    public ProtectableOwner getHolder() {
        return this.holder;
    }
}
