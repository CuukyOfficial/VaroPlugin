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
import org.bukkit.World;

public class NopVaroBorder implements VaroBorder {
    
    private final World world;

    public NopVaroBorder(World world) {
        this.world = world;
    }
    
    @Override
    public double getSize() {
        return 0;
    }

    @Override
    public void setSize(double size, long time) {
        // nop
    }

    @Override
    public Location getCenter() {
        return new Location(this.world, 0, 0, 0);
    }

    @Override
    public void setCenter(Location location) {
        // nop
    }

    @Override
    public double getDistance(Location location) {
        return 0;
    }

    @Override
    public boolean isOutside(Location location) {
        return false;
    }
}
