package de.cuuky.varo.gui;

import java.util.List;

import org.bukkit.entity.Player;

import de.varoplugin.cfw.inventory.AdvancedInventoryManager;
import de.varoplugin.cfw.inventory.list.AdvancedAsyncListInventory;

public abstract class VaroAsyncListInventory<T> extends AdvancedAsyncListInventory<T> {

    public VaroAsyncListInventory(AdvancedInventoryManager manager, Player player, List<T> list) {
        super(manager, player, list);
    }
}