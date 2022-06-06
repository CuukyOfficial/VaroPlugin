package de.varoplugin.varo.game.world.secureable;

import de.varoplugin.varo.game.entity.player.VaroPlayer;

/**
 * Represents an object which can save chests.
 *
 * @author CuukyOfficial
 * @version v0.1
 */
public interface SecureableHolder extends SecureableContainer {

    boolean canAccessSavings(VaroPlayer player);

}