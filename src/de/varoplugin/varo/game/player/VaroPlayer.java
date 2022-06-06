package de.varoplugin.varo.game.player;

import de.varoplugin.varo.game.Varo;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Represents a player playing a Varo.
 *
 * @author CuukyOfficial
 * @version v0.1
 */
public interface VaroPlayer {

    /**
     * Initializes the player.
     *
     * @param varo The game the player is playing
     */
    void initialize(Varo varo);

    /**
     * Registers the listeners of this state for this player.
     *
     * @param state The state
     */
    void registerListener(VaroPlayerState state);

    /**
     * Returns the hashCode of the UUID.
     *
     * @return hashCode of the UUID
     */
    int hashCode();

    /**
     * Returns if the player is online.
     *
     * @return If the player is online
     */
    boolean isOnline();

    /**
     * Returns the game this player is playing.
     *
     * @return The Varo
     */
    Varo getVaro();

    UUID getUuid();

    boolean setState(VaroPlayerState state);

    VaroPlayerState getState();

    void setPlayer(Player player);

    Player getPlayer();

}