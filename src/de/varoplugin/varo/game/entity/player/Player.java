package de.varoplugin.varo.game.entity.player;

import de.varoplugin.varo.game.entity.Entity;
import de.varoplugin.varo.game.entity.team.Teamable;
import de.varoplugin.varo.game.strike.Strikable;

/**
 * Represents a player playing a Varo.
 */
public interface Player extends Entity, Teamable, StatsHolder, Strikable {

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

    boolean isPlayer(org.bukkit.entity.Player player);

    boolean setState(ParticipantState state);

    ParticipantState getState();

    boolean setMode(PlayerMode mode);

    PlayerMode getMode();

    void setPlayer(org.bukkit.entity.Player player);

    org.bukkit.entity.Player getPlayer();

}