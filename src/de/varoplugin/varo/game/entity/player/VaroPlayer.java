package de.varoplugin.varo.game.entity.player;

import de.varoplugin.varo.game.entity.VaroEntity;
import de.varoplugin.varo.game.entity.team.VaroTeamable;
import org.bukkit.entity.Player;

/**
 * Represents a player playing a Varo.
 */
public interface VaroPlayer extends VaroEntity, VaroTeamable, StatsHolder {

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

    boolean setState(VaroParticipantState state);

    VaroParticipantState getState();

    boolean setMode(VaroPlayerMode mode);

    VaroPlayerMode getMode();

    void setPlayer(Player player);

    Player getPlayer();

}