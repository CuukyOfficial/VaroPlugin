package de.varoplugin.varo.game.world.protectable;

import de.varoplugin.varo.game.Varo;
import org.bukkit.block.Block;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class VaroBlockProtectable implements VaroProtectable {

    private Varo varo;
    private final ProtectableHolder holder;
    private final Block block;

    public VaroBlockProtectable(ProtectableHolder holder, Block block) {
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
    public ProtectableHolder getHolder() {
        return this.holder;
    }

}