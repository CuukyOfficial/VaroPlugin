package de.varoplugin.varo.game.entity.player;

import de.varoplugin.varo.game.VaroState;
import de.varoplugin.varo.game.TaskProvider;
import de.varoplugin.varo.game.entity.player.task.provider.VaroPlayerStateTaskProvider;

/**
 * Represents a player state a @{@link VaroPlayer} can be.
 * It provides @{@link TaskProvider} to let the player know how to behave.
 *
 * @author CuukyOfficial
 * @version v0.1
 */
public interface VaroPlayerState {

    VaroPlayerStateTaskProvider getInfo(VaroState state);

    boolean addInfo(VaroState state, VaroPlayerStateTaskProvider taskProvider);

}