package de.cuuky.varo.gui;

import de.cuuky.cfw.hooking.HookManager;
import de.cuuky.cfw.inventory.AdvancedInventoryManager;
import de.cuuky.cfw.inventory.list.AdvancedAsyncListInventory;
import de.cuuky.varo.Main;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class VaroAsyncListInventory<T> extends AdvancedAsyncListInventory<T> {

    public VaroAsyncListInventory(AdvancedInventoryManager manager, Player player, List<T> list) {
        super(manager, player, list);
    }

    public VaroAsyncListInventory(AdvancedInventoryManager manager, Player player) {
        super(manager, player);
    }

    @Override
    protected HookManager getHookManager() {
        return Main.getCuukyFrameWork().getHookManager();
    }
}