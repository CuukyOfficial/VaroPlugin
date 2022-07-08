package de.varoplugin.varo.game.world.protectable;

import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.VaroGameObject;
import org.bukkit.block.Block;

public interface Protectable extends VaroGameObject {

    /**
     * Returns the hashCode of the location.
     *
     * @return the hashCode
     */
    int hashCode();

    void initialize(Varo varo);

    Block getBlock();

    ProtectableHolder getHolder();

}