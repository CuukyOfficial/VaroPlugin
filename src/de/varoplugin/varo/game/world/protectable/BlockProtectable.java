package de.varoplugin.varo.game.world.protectable;

import de.varoplugin.varo.api.event.game.world.protectable.ProtectableInitializedEvent;
import de.varoplugin.varo.game.UniqueGameObject;
import de.varoplugin.varo.game.Varo;
import org.bukkit.block.Block;

import java.util.UUID;

final class BlockProtectable extends UniqueGameObject implements Protectable {

    private final Block block;
    private ProtectableHolder holder;

    BlockProtectable(UUID uuid, Block block) {
        super(uuid);
        this.block = block;
    }

    @Override
    public void initialize(Varo varo) {
        super.initialize(varo);
        varo.getPlugin().callEvent(new ProtectableInitializedEvent(this));
    }

    @Override
    public int hashCode() {
        return this.block.getLocation().hashCode();
    }

    @Override
    public Block getBlock() {
        return this.block;
    }

    @Override
    public void setHolder(ProtectableHolder holder) {
        this.holder = holder;
    }

    @Override
    public ProtectableHolder getHolder() {
        return this.holder;
    }
}