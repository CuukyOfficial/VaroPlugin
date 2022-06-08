package de.varoplugin.varo.game.world.protectable;

import org.bukkit.block.Block;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public interface ProtectableContainer {

    boolean addProtectable(VaroProtectable protectable);

    boolean removeProtectable(VaroProtectable protectable);

    boolean hasProtectable(VaroProtectable protectable);

    VaroProtectable getProtectable(Block block);

}