package de.cuuky.varo.gui;

import de.cuuky.cfw.inventory.AdvancedInventory;
import de.cuuky.cfw.inventory.AdvancedInventoryManager;
import de.cuuky.cfw.inventory.confirm.ConfirmInventory;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class VaroConfirmInventory extends ConfirmInventory  {
    public VaroConfirmInventory(AdvancedInventoryManager manager, Player player, String title, Consumer<Boolean> result) {
        super(manager, player, title, result);
        this.addProvider(new VaroInventoryConfigProvider(this));
    }

    public VaroConfirmInventory(AdvancedInventory from, String title, Consumer<Boolean> result) {
        super(from, title, result);
        this.addProvider(new VaroInventoryConfigProvider(this));
    }
}