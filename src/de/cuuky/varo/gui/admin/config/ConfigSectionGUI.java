package de.cuuky.varo.gui.admin.config;

import java.util.Arrays;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSettingSection;
import de.cuuky.varo.gui.VaroListInventory;
import de.varoplugin.cfw.inventory.ItemClick;
import de.varoplugin.cfw.item.ItemBuilder;

public class ConfigSectionGUI extends VaroListInventory<ConfigSettingSection> {

    public ConfigSectionGUI(Player opener) {
        super(Main.getInventoryManager(), opener, Arrays.asList(ConfigSettingSection.values()));
    }

    @Override
    protected ItemStack getItemStack(ConfigSettingSection section) {
        return ItemBuilder.material(section.getMaterial()).displayName("§7" + section.getName()).deleteDamageAnnotation()
                .lore(String.join("\n" + Main.getColorCode(), (Main.getColorCode() + section.getDescription()).split("\n"))).build();
    }

    @Override
    protected ItemClick getClick(ConfigSettingSection section) {
        return (event) -> this.openNext(new ConfigGUI(this.getPlayer(), section));
    }

    @Override
    public String getTitle() {
        return Main.getColorCode() + "Config-Sections";
    }

    @Override
    public int getSize() {
        return this.getRecommendedSize();
    }
}