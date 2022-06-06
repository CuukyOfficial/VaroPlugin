package de.varoplugin.varo.game.world.secureable;

import de.varoplugin.varo.game.Varo;
import org.bukkit.block.Block;

// TODO: Add listener for adding secureables.
/**
 * @author CuukyOfficial
 * @version v0.1
 */
public interface VaroSecureable {

    /**
     * Returns the hashCode of the location.
     *
     * @return the hashCode
     */
    int hashCode();

    void initialize(Varo varo);

    void registerListeners(VaroSecureableType type);

    Block getBlock();

}