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

package de.varoplugin.varo;

import de.varoplugin.varo.config.VaroConfig;
import de.varoplugin.varo.enchantment.BootstrapEnchantmentBlocker;
import io.github.almightysatan.jaskl.InvalidTypeException;
import io.github.almightysatan.jaskl.ValidationException;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.registry.keys.EnchantmentKeys;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class VaroBootstrap implements PluginBootstrap {

    @Override
    public void bootstrap(@NotNull BootstrapContext context) {
        context.getLogger().info("Running Varo bootstrap");
        
        Main.setPaper(true);

        if (supportsEnchantmentKeys()) {
            try {
                VaroConfig.load();
            } catch (IllegalStateException | InvalidTypeException | ValidationException | IOException e) {
                context.getLogger().error("Unable to load config", e);
                return;
            }

            BootstrapEnchantmentBlocker.blockEnchantments(context);
        }
    }
    
    private static boolean supportsEnchantmentKeys() {
        try {
            EnchantmentKeys.class.getName();
            return true;
        } catch (NoClassDefFoundError e) {
            return false;
        }
    }
}
