package de.varoplugin.varo.game.entity.player.info;

import de.varoplugin.varo.game.CancelableTask;
import de.varoplugin.varo.game.VaroState;
import de.varoplugin.varo.game.entity.player.VaroPlayer;
import org.bukkit.GameMode;

import java.util.List;

/**
 * Provides any info the player currently needs.
 * The info depends on the current player state and game state.
 *
 * @author CuukyOfficial
 * @version v0.1
 */
public interface PlayerInfo {

    // TODO: Better system. This is not optimal yet.
    List<CancelableTask> getTasks(VaroPlayer player);

    /**
     * Returns the hashCode of the state this object is providing the info of.
     *
     * @return the hashCode
     */
    int hashCode();

    /**
     * Returns game mode of the info.
     *
     * @return the GameMode
     */
    GameMode getGameMode();

    /**
     * The state this info belongs to.
     *
     * @return the state
     */
    VaroState getState();

}