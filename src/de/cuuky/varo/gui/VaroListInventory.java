package de.cuuky.varo.gui;

import de.cuuky.cfw.inventory.AdvancedInventoryManager;
import de.cuuky.cfw.inventory.ItemInserter;
import de.cuuky.cfw.inventory.inserter.AnimatedClosingInserter;
import de.cuuky.cfw.inventory.inserter.DirectInserter;
import de.cuuky.cfw.inventory.list.AdvancedListInventory;
import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.logger.logger.EventLogger;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class VaroListInventory<T> extends AdvancedListInventory<T> {

    public VaroListInventory(AdvancedInventoryManager manager, Player player, List<T> list) {
        super(manager, player, list);
    }

    public VaroListInventory(AdvancedInventoryManager manager, Player player) {
        super(manager, player);
    }

    @Override
    protected String getEmptyName() {
        if (this.getEmptyClicked() == 200 && getPlayer().hasPermission("varo.setup"))
            Main.getDataManager().getVaroLoggerManager().getEventLogger().println(EventLogger.LogType.LOG, "An admin clicked too often!%noDiscord%");

        return super.getEmptyName();
    }

    @Override
    protected ItemInserter getInserter() {
        return ConfigSetting.GUI_INVENTORY_ANIMATIONS.getValueAsBoolean() ? new AnimatedClosingInserter() : new DirectInserter();
    }

    @Override
    protected ItemStack getFillerStack() {
        return ConfigSetting.GUI_FILL_INVENTORY.getValueAsBoolean() ? super.getFillerStack() : null;
    }
}