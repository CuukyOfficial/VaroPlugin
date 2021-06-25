package de.cuuky.varo.gui;

import de.cuuky.cfw.inventory.AdvancedInventory;
import de.cuuky.cfw.inventory.Info;
import de.cuuky.cfw.inventory.InfoProvider;
import de.cuuky.cfw.inventory.ItemInserter;
import de.cuuky.cfw.inventory.inserter.AnimatedClosingInserter;
import de.cuuky.cfw.inventory.inserter.DirectInserter;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class VaroInventoryConfigProvider implements InfoProvider {

    private AdvancedInventory inventory;

    VaroInventoryConfigProvider(AdvancedInventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public List<Info<?>> getProvidedInfos() {
        return Arrays.asList(Info.ITEM_INSERTER, Info.FILLER_STACK, Info.PLAY_SOUND);
    }

    @Override
    public ItemInserter getInserter() {
        return ConfigSetting.GUI_INVENTORY_ANIMATIONS.getValueAsBoolean() ? new AnimatedClosingInserter() : new DirectInserter();
    }

    @Override
    public ItemStack getFillerStack() {
        return ConfigSetting.GUI_FILL_INVENTORY.getValueAsBoolean() ? this.inventory.getFillerStack() : null;
    }

    @Override
    public Consumer<Player> getSoundPlayer() {
        return ConfigSetting.GUI_PLAY_SOUND.getValueAsBoolean() ? this.inventory.getSoundPlayer() : null;
    }
}