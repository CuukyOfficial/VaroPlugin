package de.cuuky.varo.gui.admin.config;

import de.cuuky.cfw.inventory.ItemClick;
import de.cuuky.cfw.utils.JavaUtils;
import de.cuuky.cfw.utils.item.BuildItem;
import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSettingSection;
import de.cuuky.varo.gui.VaroListInventory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class ConfigSectionGUI extends VaroListInventory<ConfigSettingSection> {

    public ConfigSectionGUI(Player opener) {
        super(Main.getCuukyFrameWork().getAdvancedInventoryManager(), opener, Arrays.asList(ConfigSettingSection.values()));
    }

    @Override
    protected ItemStack getItemStack(ConfigSettingSection section) {
        return new BuildItem().displayName("§7" + section.getName())
                .material(section.getMaterial())
                .lore((JavaUtils.getArgsToString(JavaUtils.addIntoEvery(section.getDescription().split("\n"), Main.getColorCode(), true),
                        "\n")).split("\n")).build();
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