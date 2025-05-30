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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.enchantments.Enchantment;

import de.varoplugin.cfw.version.VersionUtils;
import de.varoplugin.varo.config.VaroConfig;

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
    }

    public void blockEnchantments(Enchantment... enchantment) {
        List<String> blocked = new ArrayList<>(VaroConfig.ENCHANTMENT_BLOCKED.getValue());
        blocked.addAll(Arrays.stream(enchantment).map(Enchantment::getName).collect(Collectors.toList()));
        VaroConfig.ENCHANTMENT_BLOCKED.setValue(blocked);
        VaroConfig.write();
        this.enchantmentBlocker.update(blocked);
    }

    public void unblockEnchantments(Enchantment... enchantment) {
        List<String> blocked = new ArrayList<>(VaroConfig.ENCHANTMENT_BLOCKED.getValue());
        blocked.removeAll(Arrays.stream(enchantment).map(Enchantment::getName).collect(Collectors.toList()));
        VaroConfig.ENCHANTMENT_BLOCKED.setValue(blocked);
        VaroConfig.write();
        this.enchantmentBlocker.update(blocked);
    }

    public boolean isBlocked(Enchantment enchantment) {
        return VaroConfig.ENCHANTMENT_BLOCKED.getValue().contains(enchantment.getName());
    }
}
