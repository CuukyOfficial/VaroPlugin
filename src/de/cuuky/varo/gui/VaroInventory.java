package de.cuuky.varo.gui;

import de.cuuky.cfw.inventory.AdvancedInventory;
import de.cuuky.cfw.inventory.AdvancedInventoryManager;
import de.cuuky.cfw.inventory.ItemInserter;
import de.cuuky.cfw.inventory.inserter.AnimatedClosingInserter;
import de.cuuky.cfw.inventory.inserter.DirectInserter;
import de.cuuky.cfw.version.BukkitVersion;
import de.cuuky.cfw.version.VersionUtils;
import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class VaroInventory extends AdvancedInventory {

    public VaroInventory(AdvancedInventoryManager manager, Player player) {
        super(manager, player);
    }

//    @Override
//    public String getTitle() {
//        return this.getTitle().length() > 32 ? this.getTitle().substring(0, 32) : getTitle();
//    }

    @Override
    protected ItemInserter getInserter() {
        return ConfigSetting.GUI_INVENTORY_ANIMATIONS.getValueAsBoolean() ? new AnimatedClosingInserter() : new DirectInserter();
    }

    @Override
    protected ItemStack getFillerStack() {
        return ConfigSetting.GUI_FILL_INVENTORY.getValueAsBoolean() ? super.getFillerStack() : null;
    }

    protected void sendInfo() {
        getPlayer().sendMessage(Main.getPrefix() + Main.getColorCode() + "§l" + Main.getPluginName());
        getPlayer().sendMessage(Main.getPrefix() + "§7Version: " + Main.getColorCode() + Main.getInstance().getDescription().getVersion());
        getPlayer().sendMessage(Main.getPrefix() + "§7Discordserver: " + Main.getColorCode() + "https://discord.gg/CnDSVVx");
        getPlayer().sendMessage(Main.getPrefix() + "§7All rights reserved!");
    }

    protected int getFixedSize(int size) {
        if (VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_8))
            return (size < 1 ? 1 : (Math.min(size, 64)));
        else
            return size;
    }
}