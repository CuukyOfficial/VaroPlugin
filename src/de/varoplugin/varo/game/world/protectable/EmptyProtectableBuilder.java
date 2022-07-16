package de.varoplugin.varo.game.world.protectable;

import org.bukkit.block.Block;

import java.util.Objects;
import java.util.UUID;

public class EmptyProtectableBuilder implements ProtectableBuilder {

    private UUID uuid;
    private Block block;

    @Override
    public ProtectableBuilder uuid(UUID uuid) {
        this.uuid = Objects.requireNonNull(uuid);
        return this;
    }

    @Override
    public ProtectableBuilder block(Block block) {
        this.block = Objects.requireNonNull(block);
        return this;
    }

    @Override
    public Protectable create() {
        if (this.block == null) throw new IllegalArgumentException("Block may not be null");
        return new BlockProtectable(this.uuid == null ? UUID.randomUUID() : this.uuid, this.block);
    }
}
