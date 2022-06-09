package de.varoplugin.varo.game.world.protectable;

import de.varoplugin.varo.game.entity.player.VaroPlayer;

/**
 * Represents an object which can save chests.
 *
 * @author CuukyOfficial
 * @version v0.1
 */
public interface VaroProtectableHolder extends VaroProtectableContainer {

    boolean canAccessSavings(VaroPlayer player);

}