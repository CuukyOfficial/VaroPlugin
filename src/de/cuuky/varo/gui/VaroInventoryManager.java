package de.cuuky.varo.gui;

import de.varoplugin.cfw.inventory.AdvancedInventory;
import de.varoplugin.cfw.inventory.AdvancedInventoryManager;
import org.bukkit.plugin.java.JavaPlugin;

public class VaroInventoryManager extends AdvancedInventoryManager {

    public VaroInventoryManager(JavaPlugin ownerInstance) {
        super(ownerInstance);
    }

    @Override
    protected AdvancedInventory registerInventory(AdvancedInventory inventory) {
        inventory.addProvider("PlayerConfig", new VaroInventoryConfigProvider(inventory));
        return super.registerInventory(inventory);
    }
}