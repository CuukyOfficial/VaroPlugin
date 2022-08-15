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

import de.varoplugin.varo.game.entity.player.VaroPlayer;

public class CountdownTask extends AbstractHeartbeatTask {

    public CountdownTask(VaroPlayer player) {
        super(player, false);
    }

    @Override
    public void onEnable() {
        if (this.getPlayer().getCountdown() <= 0) this.getPlayer().setCountdown(300); // TODO: Configurable
        super.onEnable();
    }

    @Override
    public void run() {
        this.getPlayer().setCountdown(this.getPlayer().getCountdown() - 1);
        if (this.getPlayer().getCountdown() <= 0) {
            // TODO: Add VaroPlayer#kick method with kickReason enum
            this.runSynchronized(() -> this.getPlayer().getPlayer().kickPlayer("Your session is over!"));
        }
    }
}
