package de.varoplugin.varo.game.world.protectable;

import org.bukkit.block.Block;

import java.util.UUID;

public interface ProtectableFactory {

    ProtectableFactory uuid(UUID uuid);

    ProtectableFactory block(Block block);

    Protectable create();

}
