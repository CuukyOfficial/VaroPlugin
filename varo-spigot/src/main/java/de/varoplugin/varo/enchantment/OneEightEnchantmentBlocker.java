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

package de.varoplugin.varo.enchantment;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.enchantments.Enchantment;

import de.varoplugin.varo.Main;

public class OneEightEnchantmentBlocker implements EnchantmentBlocker {

    @Override
    public void update(List<String> blocked) {
        try {
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);

            Class<?> enchantmentClass = Class.forName("net.minecraft.server.v1_8_R3.Enchantment");
            Field enchantmentArrayField = enchantmentClass.getDeclaredField("b");
            enchantmentArrayField.setAccessible(true);
            modifiersField.setInt(enchantmentArrayField, ~Modifier.FINAL & enchantmentArrayField.getModifiers());

            Object[] filteredEnchantmentArray = Arrays.stream(Enchantment.values())
                    .filter(enchantment -> !blocked.contains(enchantment.getName()))
                    .map(enchantment -> {
                try {
                    Field targetField = enchantment.getClass().getDeclaredField("target");
                    targetField.setAccessible(true);

                    return targetField.get(enchantment);
                } catch (Throwable t) {
                    throw new RuntimeException(t);
                }
            }).toArray(size -> (Object[]) Array.newInstance(enchantmentClass, size));

            enchantmentArrayField.set(null, filteredEnchantmentArray);
        } catch (Throwable t) {
            Main.getInstance().getLogger().log(Level.SEVERE, "Unable to block enchantments", t);
        }
    }
}
