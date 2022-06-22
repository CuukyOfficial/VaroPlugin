package de.varoplugin.varo.game.world.protectable;

import org.bukkit.block.Block;

public interface VaroProtectableContainer {

    boolean addProtectable(VaroProtectable protectable);

    boolean removeProtectable(VaroProtectable protectable);

    boolean hasProtectable(VaroProtectable protectable);

    VaroProtectable getProtectable(Block block);

}