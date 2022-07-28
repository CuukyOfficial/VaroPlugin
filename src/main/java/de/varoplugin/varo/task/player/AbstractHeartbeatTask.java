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
import org.bukkit.scheduler.BukkitRunnable;

public abstract class AbstractHeartbeatTask extends AbstractPlayerExecutable {

    private static final long HEARTBEAT = 20L;

    private final boolean sync;

    public AbstractHeartbeatTask(VaroPlayer player, boolean sync) {
        super(player);
        this.sync = sync;
    }

    protected void runSynchronized(Runnable runnable) {
        new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        }.runTask(this.getPlugin());
    }

    @Override
    protected void schedule(BukkitRunnable runnable) {
        if (this.sync) runnable.runTaskTimer(this.getPlugin(), HEARTBEAT, HEARTBEAT);
        else runnable.runTaskTimerAsynchronously(this.getPlugin(), HEARTBEAT, HEARTBEAT);
    }
}
