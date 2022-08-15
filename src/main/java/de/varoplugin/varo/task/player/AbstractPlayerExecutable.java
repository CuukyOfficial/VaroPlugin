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

import de.varoplugin.varo.api.task.Task;
import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.task.VaroScheduledTask;

public abstract class AbstractPlayerExecutable extends VaroScheduledTask {

    private VaroPlayer player;

    public AbstractPlayerExecutable(VaroPlayer player) {
        super(player.getVaro());
        this.player = player;
    }

    public VaroPlayer getPlayer() {
        return this.player;
    }

    @Override
    public Task clone() {
        AbstractPlayerExecutable task = (AbstractPlayerExecutable) super.clone();
        task.player = this.player;
        return task;
    }
}
