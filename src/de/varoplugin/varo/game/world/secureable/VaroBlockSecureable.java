package de.varoplugin.varo.game.world.secureable;

import de.varoplugin.varo.game.Varo;
import org.bukkit.block.Block;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class VaroBlockSecureable implements VaroSecureable {

    private final Block block;
//    private final VaroSecureableType type;

    public VaroBlockSecureable(Block block) {
        this.block = block;
    }

    @Override
    public void initialize(Varo varo) {

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
    public Block getBlock() {
        return this.block;
    }
}