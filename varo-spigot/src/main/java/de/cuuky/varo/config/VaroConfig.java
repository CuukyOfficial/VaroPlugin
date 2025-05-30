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

package de.cuuky.varo.config;

import de.cuuky.varo.Main;
import io.github.almightysatan.jaskl.Config;
import io.github.almightysatan.jaskl.InvalidTypeException;
import io.github.almightysatan.jaskl.Type;
import io.github.almightysatan.jaskl.ValidationException;
import io.github.almightysatan.jaskl.entries.ListConfigEntry;
import io.github.almightysatan.jaskl.yaml.YamlConfig;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;

public class VaroConfig {
    
    private static final Config ENCHANTMENT_CONFIG = YamlConfig.of(new File("plugins/Varo/config/enchantments.yml"));
    
    public static final ListConfigEntry<String> ENCHANTMENT_BLOCKED = ListConfigEntry.of(ENCHANTMENT_CONFIG, "blocked", "Blocked enchantments", Arrays.asList(VaroConfigDefaults.blockedEnchantmentName), Type.STRING);

    public static void load() throws IllegalStateException, InvalidTypeException, ValidationException, IOException {
        ENCHANTMENT_CONFIG.load();
        write(ENCHANTMENT_CONFIG);
    }
    
    public static void write() {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            write(ENCHANTMENT_CONFIG);
        });
    }
    
    private static void write(Config config) {
        synchronized (config) {
            try {
                config.prune();
                config.write();
            } catch (InvalidTypeException | ValidationException | UnsupportedOperationException | IOException e) {
                Main.getInstance().getLogger().log(Level.SEVERE, "Unable to save config!", e);
            }
        }
    }
}
