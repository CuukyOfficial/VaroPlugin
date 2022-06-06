package de.varoplugin.varo.game.world.secureable;

import de.varoplugin.varo.game.Varo;
import org.bukkit.block.Block;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class VaroBlockSecureable implements VaroSecureable {

    private Varo varo;
    private final SecureableHolder holder;
    private final Block block;
    // TODO: Add type
//    private final VaroSecureableType type;

    public VaroBlockSecureable(SecureableHolder holder, Block block) {
        this.block = block;
        this.holder = holder;
    }

    @Override
    public void initialize(Varo varo) {
        this.varo = varo;
    }

    @Override
    public void registerListeners(VaroSecureableType type) {
//        type.
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
    public SecureableHolder getHolder() {
        return this.holder;
    }

    @Override
    public VaroSecureableType getType() {
        return null;
    }
}