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

package de.varoplugin.varo.game.world.border;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface VaroBorder {

    double getSize();
    
    void setSize(double size, long time);
    
    default double getRadius() {
        return this.getSize() / 2;
    }

    Location getCenter();
    
    void setCenter(Location location);
    
    double getDistance(Location location);
    
    default double getDistance(Player player) {
        return player == null ? 0 : this.getDistance(player.getLocation());
    }
    
    boolean isOutside(Location location);
    
    default boolean isOutside(Player player) {
        return player == null ? false : this.isOutside(player.getLocation());
    }
}
