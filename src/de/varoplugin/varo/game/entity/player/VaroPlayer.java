package de.varoplugin.varo.game.entity.player;

import de.varoplugin.varo.game.entity.VaroEntity;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Represents a player playing a Varo.
 */
public interface VaroPlayer extends VaroEntity, StatsHolder {

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

    UUID getUuid();

    boolean setState(VaroParticipantState state);

    VaroParticipantState getState();

    boolean setMode(VaroPlayerMode mode);

    VaroPlayerMode getMode();

    void setPlayer(Player player);

    Player getPlayer();

}