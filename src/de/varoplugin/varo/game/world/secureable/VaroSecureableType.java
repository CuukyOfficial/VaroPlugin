package de.varoplugin.varo.game.world.secureable;

import de.varoplugin.varo.game.CancelableTask;
import de.varoplugin.varo.game.VaroState;

import java.util.Collection;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public interface VaroSecureableType {

    Collection<CancelableTask> getTasks(VaroState state, VaroSecureable secureable);

}