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

import org.bukkit.plugin.Plugin;

public abstract class RunnableTask implements Task {

    private Plugin plugin;
    private boolean registered;

    public RunnableTask(Plugin plugin) {
        this.plugin = plugin;
    }

    protected abstract void onEnable();

    protected abstract void onDisable();

    @Override
    public final void enable() {
        this.registered = true;
        this.onEnable();
    }

    @Override
    public final void disable() {
        this.registered = false;
        this.onDisable();
    }

    @Override
    public boolean isEnabled() {
        return this.registered;
    }

    @Override
    public Plugin getPlugin() {
        return this.plugin;
    }

    @Override
    public Task clone() {
        try {
            RunnableTask task = (RunnableTask) super.clone();
            task.plugin = this.plugin;
            return task;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
