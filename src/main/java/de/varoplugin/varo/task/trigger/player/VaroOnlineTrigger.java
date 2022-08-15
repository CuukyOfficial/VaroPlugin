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

import de.varoplugin.varo.api.task.trigger.Trigger;
import de.varoplugin.varo.game.entity.player.OnlineState;
import de.varoplugin.varo.game.entity.player.VaroOnlineState;
import de.varoplugin.varo.game.entity.player.VaroPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class VaroOnlineTrigger extends AbstractPlayerTrigger {

    private OnlineState state;

    private VaroOnlineTrigger(VaroPlayer player, OnlineState state, boolean match) {
        super(player, match);
        this.state = state;
    }

    public VaroOnlineTrigger(VaroPlayer player, OnlineState state) {
        this(player, state, true);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!this.getPlayer().isPlayer(event.getPlayer())) return;
        if (this.state == null) this.triggerIf(true);
        this.triggerIf(this.state.asBoolean());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (!this.getPlayer().isPlayer(event.getPlayer())) return;
        if (this.state == null) this.triggerIf(true);
        this.triggerIf(!this.state.asBoolean());
    }

    @Override
    protected boolean isTriggered() {
        return VaroOnlineState.parse(this.getPlayer().isOnline()).equals(this.state);
    }

    @Override
    public Trigger clone() {
        VaroOnlineTrigger trigger = (VaroOnlineTrigger) super.clone();
        trigger.state = this.state;
        return trigger;
    }
}
