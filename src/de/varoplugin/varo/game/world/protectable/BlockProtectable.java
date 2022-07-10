package de.varoplugin.varo.game.world.protectable;

import de.varoplugin.varo.api.event.game.world.protectable.ProtectableInitializedEvent;
import de.varoplugin.varo.game.UniqueGameObject;
import de.varoplugin.varo.game.Varo;
import org.bukkit.block.Block;

import java.util.UUID;

public class BlockProtectable extends UniqueGameObject implements Protectable {

    private final ProtectableHolder holder;
    private final Block block;

    public BlockProtectable(UUID uuid, ProtectableHolder holder, Block block) {
        super(uuid);
        this.block = block;
        this.holder = holder;
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
    public ProtectableHolder getHolder() {
        return this.holder;
    }
}