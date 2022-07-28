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

package de.varoplugin.varo.task;

import de.varoplugin.varo.api.event.game.VaroPlayerLoginEvent;
import de.varoplugin.varo.game.VaroKickResult;
import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.KickResult;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerLoginEvent;

public class KickNonRegisteredPlayerListener extends VaroListenerTask {

    public KickNonRegisteredPlayerListener(Varo varo) {
        super(varo);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerLogin(PlayerLoginEvent event) {
        if (event.getResult() != PlayerLoginEvent.Result.ALLOWED) return;
        KickResult result = this.getVaro().getPlayer(event.getPlayer()) == null ? VaroKickResult.NOT_A_PARTICIPANT :
                VaroKickResult.ALLOWED;

        VaroPlayerLoginEvent loginKickEvent = new VaroPlayerLoginEvent(this.getVaro(), event, result);
        this.getVaro().getPlugin().callEvent(loginKickEvent);
        if (loginKickEvent.getResult() != VaroKickResult.ALLOWED) {
            event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
        }
    }
}
