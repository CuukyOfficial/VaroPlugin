package de.cuuky.varo.gui;

import de.cuuky.cfw.hooking.HookManager;
import de.cuuky.cfw.inventory.AdvancedInventoryManager;
import de.cuuky.cfw.inventory.list.AdvancedListInventory;
import de.cuuky.varo.Main;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class VaroListInventory<T> extends AdvancedListInventory<T> {

    public VaroListInventory(AdvancedInventoryManager manager, Player player, List<T> list) {
        super(manager, player, list);
        this.addProvider(new VaroInventoryConfigProvider(this));
    }

    public VaroListInventory(AdvancedInventoryManager manager, Player player) {
        super(manager, player);
        this.addProvider(new VaroInventoryConfigProvider(this));
    }

    @Override
    protected HookManager getHookManager() {
        return Main.getCuukyFrameWork().getHookManager();
    }
}