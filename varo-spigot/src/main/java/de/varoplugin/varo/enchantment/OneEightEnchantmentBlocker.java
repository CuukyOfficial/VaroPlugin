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

import de.varoplugin.varo.Main;
import org.bukkit.command.defaults.EnchantCommand;
import org.bukkit.enchantments.Enchantment;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class OneEightEnchantmentBlocker implements EnchantmentBlocker {

    private Enchantment[] ENCHANTMENTS = Enchantment.values();
    
    @Override
    public void update(Map<String, Integer> blocked) {
        try {
            // set acceptingNew
            Field bukkitAcceptingNewField = Enchantment.class.getDeclaredField("acceptingNew");
            bukkitAcceptingNewField.setAccessible(true);
            bukkitAcceptingNewField.set(null, true);

            // clear bukkit byId
            Field bukkitByIdField = Enchantment.class.getDeclaredField("byId");
            bukkitByIdField.setAccessible(true);
            ((Map<?, ?>) bukkitByIdField.get(null)).clear();

            // clear bukkit byName
            Field bukkitByNameField = Enchantment.class.getDeclaredField("byName");
            bukkitByNameField.setAccessible(true);
            ((Map<?, ?>) bukkitByNameField.get(null)).clear();

            // clear ENCHANTMENT_NAMES from EnchantCommand
            Field bukkitEnchantmentNamesField = EnchantCommand.class.getDeclaredField("ENCHANTMENT_NAMES");
            bukkitEnchantmentNamesField.setAccessible(true);
            ((List<?>) bukkitEnchantmentNamesField.get(null)).clear();

            // remove final modifier
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);

            Class<?> enchantmentClass = Class.forName("net.minecraft.server.v1_8_R3.Enchantment");
            Field enchantmentArrayField = enchantmentClass.getDeclaredField("b");
            enchantmentArrayField.setAccessible(true);
            modifiersField.setInt(enchantmentArrayField, ~Modifier.FINAL & enchantmentArrayField.getModifiers());

            // clear nms byId
            Field byIdField = enchantmentClass.getDeclaredField("byId");
            byIdField.setAccessible(true);
            Arrays.fill(((net.minecraft.server.v1_8_R3.Enchantment[]) byIdField.get(null)), null);

            // clear nms E
            Field byKeyField = enchantmentClass.getDeclaredField("E");
            byKeyField.setAccessible(true);
            ((Map<?, ?>) byKeyField.get(null)).clear();

            // create new nms enchantment objects
            List<Object> filteredEnchantments = new ArrayList<>();
            for (Enchantment enchantment : ENCHANTMENTS) {
                int maxLevel = blocked.getOrDefault(enchantment.getName(), Integer.MAX_VALUE) - 1;
                if (maxLevel < 1) {
                    Main.getInstance().getLogger().info("Blocking enchantment " + enchantment.getName());
                    continue;
                }

                try {
                    Field targetField = enchantment.getClass().getDeclaredField("target");
                    targetField.setAccessible(true);
                    net.minecraft.server.v1_8_R3.Enchantment nmsEnchantment = (net.minecraft.server.v1_8_R3.Enchantment) targetField.get(enchantment);

                    Main.getInstance().getLogger().info("Setting enchantment max level to " + Math.min(nmsEnchantment.getMaxLevel(), maxLevel) + " for " + enchantment.getName());
                    filteredEnchantments.add(new HackEnchantment(nmsEnchantment, Math.min(nmsEnchantment.getMaxLevel(), maxLevel)));
                } catch (Throwable t) {
                    throw new RuntimeException(t);
                }
            }

            enchantmentArrayField.set(null, filteredEnchantments.toArray((Object[]) Array.newInstance(enchantmentClass, filteredEnchantments.size())));

            Enchantment.stopAcceptingRegistrations();
        } catch (Throwable t) {
            Main.getInstance().getLogger().log(Level.SEVERE, "Unable to block enchantments", t);
        }
    }

    static class HackEnchantment extends net.minecraft.server.v1_8_R3.Enchantment {

        private final net.minecraft.server.v1_8_R3.Enchantment enchantment;
        private final int maxLevel;

        public HackEnchantment(net.minecraft.server.v1_8_R3.Enchantment enchantment, int maxLevel) {
            super(enchantment.id, null, enchantment.getRandomWeight(), enchantment.slot);
            this.enchantment = enchantment instanceof HackEnchantment ? ((HackEnchantment) enchantment).enchantment : enchantment;
            this.maxLevel = maxLevel;
        }

        @Override
        public int getMaxLevel() {
            return this.maxLevel;
        }
    }
}
