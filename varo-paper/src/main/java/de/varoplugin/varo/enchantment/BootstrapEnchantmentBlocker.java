package de.varoplugin.varo.enchantment;

import de.varoplugin.varo.config.VaroConfig;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.event.RegistryEvents;
import io.papermc.paper.registry.set.RegistrySet;

public class BootstrapEnchantmentBlocker {

    public static void blockEnchantments(BootstrapContext context) {
        var blockedEnchantments = VaroConfig.ENCHANTMENT_BLOCKED.getValue();
        context.getLifecycleManager().registerEventHandler(RegistryEvents.ENCHANTMENT.entryAdd()
                .newHandler(event -> {
                    String name = event.key().value(); // TODO somehow convert between bukkit and registry names?
                    int maxLevel = blockedEnchantments.getOrDefault(name, Integer.MAX_VALUE) - 1;
                    var builder = event.builder();
                    if (maxLevel < 1) {
                        // set supportedItems to an empty array to block enchantment
                        event.builder().supportedItems(RegistrySet.keySet(RegistryKey.ITEM));
                        context.getLogger().info("Blocking enchantment " + name);
                    } else if (maxLevel < builder.maxLevel()) {
                        event.builder().maxLevel(maxLevel);
                        context.getLogger().info("Setting max enchantment level to " + maxLevel + " for " + name);
                    }
                }));
    }
}
