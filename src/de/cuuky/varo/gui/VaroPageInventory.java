package de.cuuky.varo.gui;

import de.cuuky.cfw.inventory.AdvancedInventoryManager;
import de.cuuky.cfw.inventory.ItemInserter;
import de.cuuky.cfw.inventory.inserter.AnimatedClosingInserter;
import de.cuuky.cfw.inventory.inserter.DirectInserter;
import de.cuuky.cfw.inventory.page.AdvancedPageInventory;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class VaroPageInventory extends AdvancedPageInventory {

    public VaroPageInventory(AdvancedInventoryManager manager, Player player) {
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
}