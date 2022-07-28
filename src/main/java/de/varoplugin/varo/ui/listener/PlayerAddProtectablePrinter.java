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

import de.varoplugin.varo.api.event.game.world.protectable.ProtectableAddEvent;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.event.EventHandler;

public class PlayerAddProtectablePrinter extends UiListener {

    @EventHandler
    public void onProtectableAdd(ProtectableAddEvent event) {
        // Won't work for anything other than a player
        Bukkit.getPlayer(event.getProtectable().getOwner().getUuid()).sendMessage("Block saved");

        for (int i = 0; i < 6; i++)
            event.getProtectable().getBlock().getWorld().playEffect(
                    event.getProtectable().getBlock().getLocation(), Effect.ENDER_SIGNAL, 1);
    }
}
