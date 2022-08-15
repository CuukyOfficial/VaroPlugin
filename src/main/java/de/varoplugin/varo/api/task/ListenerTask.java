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

package de.varoplugin.varo.api.task;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public abstract class ListenerTask extends RunnableTask implements Task, Listener {

    public ListenerTask(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void onEnable() {
        this.getPlugin().getServer().getPluginManager().registerEvents(this, this.getPlugin());
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }
}
