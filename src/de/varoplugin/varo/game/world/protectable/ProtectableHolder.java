package de.varoplugin.varo.game.world.protectable;

import org.bukkit.block.Block;

import java.util.UUID;

/**
 * Represents an object which can save chests.
 */
public interface ProtectableHolder {

    UUID getUuid();

    boolean addProtectable(Protectable protectable);

    boolean removeProtectable(Protectable protectable);

    boolean hasProtectable(Protectable protectable);

    Protectable getProtectable(Block block);

    boolean canAccessSavings(ProtectableHolder player);

}