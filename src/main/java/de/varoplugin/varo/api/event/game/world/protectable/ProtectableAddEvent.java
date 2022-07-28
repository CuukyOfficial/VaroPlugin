package de.varoplugin.varo.api.event.game.world.protectable;

import de.varoplugin.varo.game.world.protectable.Protectable;
import de.varoplugin.varo.game.world.protectable.ProtectableOwner;

public class ProtectableAddEvent extends ProtectableHolderEvent {

    public ProtectableAddEvent(ProtectableOwner holder, Protectable protectable) {
        super(holder, protectable);
    }
}