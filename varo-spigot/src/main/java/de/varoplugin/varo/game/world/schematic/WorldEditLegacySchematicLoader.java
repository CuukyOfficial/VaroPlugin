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

package de.varoplugin.varo.game.world.schematic;

import java.io.File;

import org.bukkit.Location;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.bukkit.BukkitWorld;

public class WorldEditLegacySchematicLoader implements SchematicLoader {

    private final Class<?> vectorClass;
    private final Class<?> cuboidClipClass;
    private final Class<?> localWorldClass;

    public WorldEditLegacySchematicLoader() throws Throwable {
        vectorClass = Class.forName("com.sk89q.worldedit.Vector");
        cuboidClipClass = Class.forName("com.sk89q.worldedit.CuboidClipboard");
        localWorldClass = Class.forName("com.sk89q.worldedit.LocalWorld");
    }

    @Override
    public void paste(File file, Location location) {
        try {
            Object origin = vectorClass.getDeclaredMethod("toBlockPoint", double.class, double.class, double.class).invoke(null, location.getX(), location.getY(), location.getZ());

            EditSession es = EditSession.class.getConstructor(localWorldClass, int.class).newInstance(new BukkitWorld(location.getWorld()), 999999999);

            Object clipboard = cuboidClipClass.getDeclaredMethod("loadSchematic", File.class).invoke(null, file);
            clipboard.getClass().getMethod("paste", es.getClass(), vectorClass, boolean.class).invoke(clipboard, es, origin, false);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
