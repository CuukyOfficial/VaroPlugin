package de.varoplugin.varo.enchantment;

import de.varoplugin.varo.config.VaroConfig;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.event.RegistryEvents;
import io.papermc.paper.registry.set.RegistrySet;

public class BootstrapEnchantmentBlocker {

    public static void blockEnchantments(BootstrapContext context) {
        var blockedEnchantments = VaroConfig.ENCHANTMENT_BLOCKED.getValue();
        // set supportedItems to an empty array to block enchantment
        context.getLifecycleManager().registerEventHandler(RegistryEvents.ENCHANTMENT.entryAdd()
                .newHandler(event -> event.builder().supportedItems(RegistrySet.keySet(RegistryKey.ITEM)))
                .filter(key -> {
                    String name = key.key().value();
                    if (blockedEnchantments.contains(name)) {
                        context.getLogger().info("Blocking enchantment " + name);
                        return true;
                    }
                    return false;
                }));
    }
}
