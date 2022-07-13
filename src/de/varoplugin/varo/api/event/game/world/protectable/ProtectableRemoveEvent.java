package de.varoplugin.varo.api.event.game.world.protectable;

import de.varoplugin.varo.game.world.protectable.Protectable;
import de.varoplugin.varo.game.world.protectable.ProtectableHolder;

public class ProtectableRemoveEvent extends ProtectableHolderEvent {

    public ProtectableRemoveEvent(ProtectableHolder holder, Protectable protectable) {
        super(holder, protectable);
    }
}