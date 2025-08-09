package de.varoplugin.varo.config;

import com.cryptomorin.xseries.XEnchantment;
import org.bukkit.Bukkit;

// TODO this needs a better solution
public class VaroConfigDefaults {

    public static String blockedEnchantmentName = "BANE_OF_ARTHROPODS";

    static {
        if (Bukkit.getServer() != null) { // server is null when running unit tests
            blockedEnchantmentName = XEnchantment.BANE_OF_ARTHROPODS.get().getName();
        }
    }
}
