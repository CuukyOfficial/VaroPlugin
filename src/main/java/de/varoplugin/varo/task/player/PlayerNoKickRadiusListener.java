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

package de.varoplugin.varo.task.player;

import de.varoplugin.varo.api.event.game.player.PlayerCountdownChangeEvent;
import de.varoplugin.varo.game.entity.player.VaroPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;

public class PlayerNoKickRadiusListener extends AbstractPlayerListener {

    private final int checkCountdown;
    private final int checkRadius;

    public PlayerNoKickRadiusListener(VaroPlayer player) {
        super(player);

        this.checkCountdown = 5;
        this.checkRadius = 30;
        // TODO: Configurable
    }

    @EventHandler
    public void onPlayerKick(PlayerCountdownChangeEvent event) {
        if (!event.getPlayer().equals(this.getPlayer()) || event.getCountdown() >= this.checkCountdown) return;

        for (Entity entity : event.getPlayer().getPlayer().getNearbyEntities(this.checkRadius, this.checkRadius, this.checkRadius)) {
            if (entity.getType() != EntityType.PLAYER) return;
            VaroPlayer other = this.getVaro().getPlayer(entity.getUniqueId());
            if (other.getTeam() != this.getPlayer().getTeam()) {
                // TODO: Add event
                event.setCountdown(this.checkCountdown);
            }
        }
    }
}
