package de.varoplugin.varo.api.event.game.world.protectable;

import de.varoplugin.varo.game.world.protectable.Protectable;
import de.varoplugin.varo.game.world.protectable.ProtectableHolder;

public class ProtectableAddEvent extends ProtectableHolderEvent {

    public ProtectableAddEvent(ProtectableHolder holder, Protectable protectable) {
        super(holder, protectable);
    }
}