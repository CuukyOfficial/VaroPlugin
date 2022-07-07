package de.varoplugin.varo.game.world.protectable;

import de.varoplugin.varo.api.event.game.world.protectable.VaroProtectableInitializedEvent;
import de.varoplugin.varo.game.GameObject;
import de.varoplugin.varo.game.Varo;
import org.bukkit.block.Block;

public class BlockProtectable extends GameObject implements Protectable {

    private final ProtectableHolder holder;
    private final Block block;

    public BlockProtectable(ProtectableHolder holder, Block block) {
        this.block = block;
        this.holder = holder;
    }

    @Override
    public void initialize(Varo varo) {
        super.initialize(varo);
        varo.getPlugin().callEvent(new VaroProtectableInitializedEvent(this));
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