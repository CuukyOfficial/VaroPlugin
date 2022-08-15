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

package de.varoplugin.varo.task.trigger.player;

import de.varoplugin.varo.api.event.game.player.PlayerRemoveEvent;
import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.api.task.trigger.Trigger;
import de.varoplugin.varo.task.trigger.GameTrigger;
import org.bukkit.event.EventHandler;

public abstract class AbstractPlayerTrigger extends GameTrigger {

    private VaroPlayer player;

    public AbstractPlayerTrigger(VaroPlayer player, boolean match) {
        super(player.getVaro(), match);
        this.player = player;
    }

    @EventHandler
    public void onPlayerRemove(PlayerRemoveEvent event) {
        if (!this.player.equals(event.getPlayer())) return;
        this.deactivate();
    }

    public VaroPlayer getPlayer() {
        return this.player;
    }

    @Override
    public Trigger clone() {
        AbstractPlayerTrigger trigger = (AbstractPlayerTrigger) super.clone();
        trigger.player = this.player;
        return trigger;
    }
}
