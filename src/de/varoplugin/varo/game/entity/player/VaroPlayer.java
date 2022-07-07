package de.varoplugin.varo.game.entity.player;

import de.varoplugin.varo.game.entity.VaroEntity;
import de.varoplugin.varo.game.entity.team.VaroTeamable;
import de.varoplugin.varo.game.strike.VaroStrikable;
import org.bukkit.entity.Player;

/**
 * Represents a player playing a Varo.
 */
public interface VaroPlayer extends VaroEntity, VaroTeamable, StatsHolder, VaroStrikable {

    /**
     * Returns the hashCode of the UUID.
     *
     * @return hashCode of the UUID
     */
    int hashCode();

    void setCountdown(int countdown);

    int getCountdown();

    /**
     * Returns if the player is online.
     *
     * @return If the player is online
     */
    boolean isOnline();

    boolean isPlayer(Player player);

    boolean setState(VaroParticipantState state);

    VaroParticipantState getState();

    boolean setMode(VaroPlayerMode mode);

    VaroPlayerMode getMode();

    void setPlayer(Player player);

    Player getPlayer();

}