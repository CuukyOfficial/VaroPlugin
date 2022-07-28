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

package de.varoplugin.varo.ui.commands;

import de.varoplugin.varo.VaroPlugin;
import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.ui.UiElement;
import org.bukkit.command.CommandExecutor;

// ONLY FOR TESTS
public abstract class VaroCommand implements UiElement, CommandExecutor {

    private final String name;
    private VaroPlugin plugin;

    public VaroCommand(String name) {
        this.name = name;
    }

    protected Varo getVaro() {
        return this.plugin.getVaro();
    }

    @Override
    public void register(VaroPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginCommand(this.name).setExecutor(this);
    }
}
