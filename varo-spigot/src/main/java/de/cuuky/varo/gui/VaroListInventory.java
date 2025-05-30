package de.cuuky.varo.gui;

import java.util.List;

import org.bukkit.entity.Player;

import de.varoplugin.cfw.inventory.AdvancedInventoryManager;
import de.varoplugin.cfw.inventory.list.AdvancedListInventory;

public abstract class VaroListInventory<T> extends AdvancedListInventory<T> {

    public VaroListInventory(AdvancedInventoryManager manager, Player player, List<T> list) {
        super(manager, player, list);
    }
}