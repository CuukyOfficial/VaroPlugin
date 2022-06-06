package de.varoplugin.varo.game.world.secureable;

import de.varoplugin.varo.game.CancelableTask;
import de.varoplugin.varo.game.VaroState;

import java.util.Collection;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public enum VaroBlockSecureableType implements VaroSecureableType {

    FURNACE,
    CHEST;

    @Override
    public Collection<CancelableTask> getTasks(VaroState state) {
//        if ()
        return null;
    }
}