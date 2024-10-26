package de.cuuky.varo.gui;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.cryptomorin.xseries.XMaterial;

import de.varoplugin.cfw.inventory.AdvancedInventoryManager;
import de.varoplugin.cfw.inventory.list.AdvancedAsyncListInventory;
import de.varoplugin.cfw.item.ItemBuilder;

public abstract class VaroPlayerAsyncListInventory<T> extends AdvancedAsyncListInventory<T> {

    public VaroPlayerAsyncListInventory(AdvancedInventoryManager manager, Player player, List<T> list) {
        super(manager, player, list);
    }
    
    @Override
    protected ItemStack getLoadingItem() {
        return ItemBuilder.material(XMaterial.SKELETON_SKULL).displayName("§cLoading...").build();
    }
}