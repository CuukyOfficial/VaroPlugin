package de.varoplugin.varo.game.world.secureable;

import org.bukkit.block.Block;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public interface SecureableContainer {

    boolean addSecureable(VaroSecureable secureable);

    boolean removeSecureable(VaroSecureable secureable);

    boolean hasSecureable(VaroSecureable secureable);

    VaroSecureable getSavable(Block block);

}