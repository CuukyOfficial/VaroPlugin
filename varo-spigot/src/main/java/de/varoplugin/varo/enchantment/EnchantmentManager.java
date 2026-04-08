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

import de.varoplugin.cfw.version.VersionUtils;
import de.varoplugin.varo.config.VaroConfig;
import org.bukkit.enchantments.Enchantment;

import java.util.HashMap;
import java.util.Map;

public class EnchantmentManager {

    private final EnchantmentBlocker enchantmentBlocker;

    public EnchantmentManager() {
        switch (VersionUtils.getVersion()) {
            case ONE_8:
                this.enchantmentBlocker = new OneEightEnchantmentBlocker();
                break;
            default:
                this.enchantmentBlocker = new NopEnchantmentBlocker();
                break;
        }
        this.enchantmentBlocker.update(VaroConfig.ENCHANTMENT_BLOCKED.getValue());
    }

    public void blockEnchantments(Map<Enchantment, Integer> enchantments) {
        Map<String, Integer> blocked = new HashMap<>(VaroConfig.ENCHANTMENT_BLOCKED.getValue());
        for (Map.Entry<Enchantment, Integer> enchantment : enchantments.entrySet())
            blocked.put(enchantment.getKey().getName(), enchantment.getValue());
        VaroConfig.ENCHANTMENT_BLOCKED.setValue(blocked);
        VaroConfig.write();
        this.enchantmentBlocker.update(VaroConfig.ENCHANTMENT_BLOCKED.getValue());
    }

    public void unblockEnchantments(Enchantment... enchantments) {
        Map<String, Integer> blocked = new HashMap<>(VaroConfig.ENCHANTMENT_BLOCKED.getValue());
        for (Enchantment enchantment : enchantments)
            blocked.remove(enchantment.getName());
        VaroConfig.ENCHANTMENT_BLOCKED.setValue(blocked);
        VaroConfig.write();
        this.enchantmentBlocker.update(VaroConfig.ENCHANTMENT_BLOCKED.getValue());
    }

    public boolean isBlocked(Enchantment enchantment, int level) {
        return VaroConfig.ENCHANTMENT_BLOCKED.getValue().getOrDefault(enchantment.getName(), Integer.MAX_VALUE) <= level;
    }
}
