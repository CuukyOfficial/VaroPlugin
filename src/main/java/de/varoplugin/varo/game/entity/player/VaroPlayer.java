/* 
 * VaroPlugin
 * Copyright (C) 2022 Cuuky, Almighty-Satan
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/

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