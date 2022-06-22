package de.varoplugin.varo.game.world.protectable;

import de.varoplugin.varo.game.Varo;
import org.bukkit.block.Block;

public class BlockProtectable implements VaroProtectable {

    private Varo varo;
    private final VaroProtectableHolder holder;
    private final Block block;

    public BlockProtectable(VaroProtectableHolder holder, Block block) {
        this.block = block;
        this.holder = holder;
    }

    @Override
    public void initialize(Varo varo) {
        this.varo = varo;
    }

    @Override
    public int hashCode() {
        return this.block.getLocation().hashCode();
    }

    @Override
    public Varo getVaro() {
        return this.varo;
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