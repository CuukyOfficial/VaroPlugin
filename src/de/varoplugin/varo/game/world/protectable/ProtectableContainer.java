package de.varoplugin.varo.game.world.protectable;

import org.bukkit.block.Block;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public interface ProtectableContainer {

    boolean addSecureable(VaroProtectable secureable);

    boolean removeSecureable(VaroProtectable secureable);

    boolean hasSecureable(VaroProtectable secureable);

    VaroProtectable getSavable(Block block);

}