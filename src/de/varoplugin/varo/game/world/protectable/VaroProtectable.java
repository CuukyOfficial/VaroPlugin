package de.varoplugin.varo.game.world.protectable;

import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.VaroGameObject;
import org.bukkit.block.Block;

// TODO: Add listener for adding secureables.
/**
 * @author CuukyOfficial
 * @version v0.1
 */
public interface VaroProtectable extends VaroGameObject {

    /**
     * Returns the hashCode of the location.
     *
     * @return the hashCode
     */
    int hashCode();

    void initialize(Varo varo);

    Block getBlock();

    VaroProtectableHolder getHolder();

}