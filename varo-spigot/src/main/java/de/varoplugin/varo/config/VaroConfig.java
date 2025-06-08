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

package de.varoplugin.varo.config;

import de.varoplugin.varo.Main;
import de.varoplugin.varo.player.stats.stat.StrikeTemplate;
import io.github.almightysatan.jaskl.*;
import io.github.almightysatan.jaskl.entries.BooleanConfigEntry;
import io.github.almightysatan.jaskl.entries.ListConfigEntry;
import io.github.almightysatan.jaskl.yaml.YamlConfig;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.logging.Level;

public class VaroConfig {
    
    private static boolean loaded;

    private static final Config ENCHANTMENT_CONFIG = YamlConfig.of(new File("plugins/Varo/config/enchantments.yml"));
    public static final ListConfigEntry<String> ENCHANTMENT_BLOCKED = ListConfigEntry.of(ENCHANTMENT_CONFIG, "blocked", "Blocked enchantments", Collections.singletonList(VaroConfigDefaults.blockedEnchantmentName), Type.STRING);

    private static final Config STRIKE_CONFIG = YamlConfig.of(new File("plugins/Varo/config/strikes.yml"));
    public static final BooleanConfigEntry STRIKE_POST_AT_REST = BooleanConfigEntry.of(STRIKE_CONFIG, "postAtResetHour", "Whether strikes should only be posted at the reset hour.", false);
    public static final BooleanConfigEntry STRIKE_CLEAR_ARMOR = BooleanConfigEntry.of(STRIKE_CONFIG, "clearArmor", "Whether strikes should also clear the armor slots when clearing a player's inventory.", false);
    public static final ListConfigEntry<StrikeTemplate> STRIKE_TEMPLATES = ListConfigEntry.of(STRIKE_CONFIG, "templates", "", StrikeTemplate.getDefaultStrikeTemplates(), Type.custom(StrikeTemplate.class), Validator.listNotEmpty());

    private static final Config[] CONFIGS = new Config[] {
            ENCHANTMENT_CONFIG,
            STRIKE_CONFIG
    };

    public static void load() throws IllegalStateException, InvalidTypeException, ValidationException, IOException {
        if (!loaded) {
            for (Config config : CONFIGS) {
                config.load();
                write(config);
            }
        }
        loaded = true;
    }
    
    public static void write() {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            for (Config config : CONFIGS)
                write(config);
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
