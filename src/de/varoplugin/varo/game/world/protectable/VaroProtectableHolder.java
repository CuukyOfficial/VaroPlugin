package de.varoplugin.varo.game.world.protectable;

import de.varoplugin.varo.game.VaroGameObject;
import de.varoplugin.varo.game.entity.VaroEntity;

/**
 * Represents an object which can save chests.
 */
public interface VaroProtectableHolder extends VaroProtectableContainer, VaroGameObject {

    boolean canAccessSavings(VaroEntity player);

}