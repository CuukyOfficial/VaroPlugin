package de.varoplugin.varo.game.player.state;

import de.varoplugin.varo.game.CancelableListener;
import de.varoplugin.varo.game.VaroState;
import de.varoplugin.varo.game.player.VaroPlayer;
import org.bukkit.GameMode;

import java.util.List;

/**
 * hashCode needs to return the hash of the state.
 *
 * @author CuukyOfficial
 * @version v0.1
 */
public interface PlayerInfo {

    // TODO: Better system. This is not optimal yet.
    List<CancelableListener> getListener(VaroPlayer player);

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