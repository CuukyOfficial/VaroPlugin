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

package de.varoplugin.varo.ui.listener;

import de.varoplugin.varo.api.event.game.VaroPlayerLoginEvent;
import de.varoplugin.varo.api.event.game.player.PlayerLoginEvent;
import de.varoplugin.varo.game.VaroKickResult;
import de.varoplugin.varo.game.KickResult;
import org.bukkit.event.EventHandler;

import java.util.Arrays;

public class PlayerKickListener extends UiListener {

    private enum KickMessages {

        // TODO: Use language system
        NOT_A_PARTICIPANT(VaroKickResult.NOT_A_PARTICIPANT, "You are not registered in this Varo!");

        private final KickResult result;
        private final String message;

        KickMessages(KickResult result, String message) {
            this.result = result;
            this.message = message;
        }

        public String getMessage() {
            return this.message;
        }

        public static KickMessages getMessage(KickResult result) {
            return Arrays.stream(values()).filter(m -> m.result.equals(result)).findAny().orElse(null);
        }
    }

    @EventHandler
    public void onVaroPlayerKick(PlayerLoginEvent event) {
        if (event.getResult() == de.varoplugin.varo.game.entity.player.VaroKickResult.PLAYER_DEAD) {
            event.getSource().setKickMessage("You are dead!");
        }
     }

    @EventHandler
    public void onGamePlayerKick(VaroPlayerLoginEvent event) {
        KickMessages message = KickMessages.getMessage(event.getResult());
        if (message == null) return;
        event.getSource().setKickMessage(message.getMessage());
    }
}