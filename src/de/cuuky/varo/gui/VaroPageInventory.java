package de.cuuky.varo.gui;

import de.cuuky.cfw.inventory.AdvancedInventoryManager;
import de.cuuky.cfw.inventory.page.AdvancedPageInventory;
import org.bukkit.entity.Player;

public abstract class VaroPageInventory extends AdvancedPageInventory {

    public VaroPageInventory(AdvancedInventoryManager manager, Player player) {
        super(manager, player);
        this.addProvider(new VaroInventoryConfigProvider(this));
    }
}