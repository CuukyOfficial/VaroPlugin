package de.varoplugin.varo.game.entity.player;

import de.varoplugin.varo.game.entity.VaroEntity;
import de.varoplugin.varo.game.entity.StatsHolder;
import de.varoplugin.varo.game.entity.player.session.SessionHolder;
import de.varoplugin.varo.game.entity.team.Teamable;
import de.varoplugin.varo.game.strike.Strikable;
import org.bukkit.entity.Player;

/**
 * Represents a player playing a Varo.
 */
public interface VaroPlayer extends VaroEntity, Teamable, StatsHolder, Strikable, SessionHolder {

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

    boolean setState(ParticipantState state);

    ParticipantState getState();

    boolean setMode(PlayerMode mode);

    PlayerMode getMode();

    void setPlayer(Player player);

    Player getPlayer();

}