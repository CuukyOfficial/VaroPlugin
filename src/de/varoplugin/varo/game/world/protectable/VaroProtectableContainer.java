package de.varoplugin.varo.game.world.protectable;

import de.varoplugin.varo.game.VaroGameObject;
import org.bukkit.block.Block;

public interface VaroProtectableContainer extends VaroGameObject {

    boolean addProtectable(VaroProtectable protectable);

    boolean removeProtectable(VaroProtectable protectable);

    boolean hasProtectable(VaroProtectable protectable);

    VaroProtectable getProtectable(Block block);

}