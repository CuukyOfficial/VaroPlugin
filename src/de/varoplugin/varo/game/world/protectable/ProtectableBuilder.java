package de.varoplugin.varo.game.world.protectable;

import org.bukkit.block.Block;

import java.util.UUID;

public interface ProtectableBuilder {

    ProtectableBuilder uuid(UUID uuid);

    ProtectableBuilder block(Block block);

    Protectable create();

}
