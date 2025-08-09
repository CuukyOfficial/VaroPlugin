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

public interface RectangularVaroBorder extends VaroBorder {

    @Override
    default double getDistance(Location location) {
        Location center = this.getCenter();
        double size = this.getSize() / 2;
        double dx = Math.abs(location.getX() - center.getX()) - size;
        double dz = Math.abs(location.getZ() - center.getZ()) - size;
        if (dx <= 0 || dz <= 0)
            return Math.abs(Math.max(dx, dz));
        return Math.sqrt(dx * dx + dz * dz);
    }
    
    @Override
    default boolean isOutside(Location location) {
        Location center = this.getCenter();
        double size = this.getSize() / 2;
        double dx = Math.abs(location.getX() - center.getX()) - size;
        double dz = Math.abs(location.getZ() - center.getZ()) - size;
        return dx > 0 || dz > 0;
    }
}
