package de.varoplugin.varo.game.world.protectable;

import de.varoplugin.varo.game.entity.VaroEntity;

import java.util.UUID;

/**
 * Represents an object which can save chests.
 */
public interface VaroProtectableHolder extends VaroProtectableContainer {

    UUID getUuid();

    boolean canAccessSavings(VaroEntity player);

}