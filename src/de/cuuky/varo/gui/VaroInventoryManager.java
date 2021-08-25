package de.cuuky.varo.gui;

import de.cuuky.cfw.inventory.AdvancedInventory;
import de.cuuky.cfw.inventory.AdvancedInventoryManager;
import org.bukkit.plugin.java.JavaPlugin;

public class VaroInventoryManager extends AdvancedInventoryManager {

    public VaroInventoryManager(JavaPlugin ownerInstance) {
        super(ownerInstance);
    }

    @Override
    protected AdvancedInventory registerInventory(AdvancedInventory inventory) {
        inventory.addProvider(new VaroInventoryConfigProvider(inventory));
        return super.registerInventory(inventory);
    }
}