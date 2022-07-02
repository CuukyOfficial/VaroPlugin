package de.varoplugin.varo.game.world.protectable;

import de.varoplugin.varo.api.event.game.world.protectable.VaroProtectableInitializedEvent;
import de.varoplugin.varo.game.GameObject;
import de.varoplugin.varo.game.Varo;
import org.bukkit.block.Block;

public class BlockProtectable extends GameObject implements VaroProtectable {

    private final VaroProtectableHolder holder;
    private final Block block;

    public BlockProtectable(VaroProtectableHolder holder, Block block) {
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
    public VaroProtectableHolder getHolder() {
        return this.holder;
    }
}