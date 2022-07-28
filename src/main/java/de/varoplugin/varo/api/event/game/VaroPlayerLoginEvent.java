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

package de.varoplugin.varo.api.event.game;

import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.KickResult;
import org.bukkit.event.player.PlayerLoginEvent;

public class VaroPlayerLoginEvent extends VaroGameEvent {

    private final PlayerLoginEvent source;
    private final KickResult result;

    public VaroPlayerLoginEvent(Varo varo, PlayerLoginEvent loginEvent, KickResult result) {
        super(varo);

        this.source = loginEvent;
        this.result = result;
    }

    public PlayerLoginEvent getSource() {
        return this.source;
    }

    public KickResult getResult() {
        return this.result;
    }
}