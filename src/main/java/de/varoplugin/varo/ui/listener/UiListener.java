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

import de.varoplugin.varo.VaroPlugin;
import de.varoplugin.varo.ui.UiElement;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.logging.Logger;

abstract class UiListener implements UiElement, Listener {

    private Plugin plugin;

    @Override
    public void register(VaroPlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public Plugin getPlugin() {
        return this.plugin;
    }

    public Logger getLogger() {
        return this.plugin.getLogger();
    }
}