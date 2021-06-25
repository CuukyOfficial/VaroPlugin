package de.cuuky.varo.gui;

import de.cuuky.cfw.inventory.AdvancedInventoryManager;
import de.cuuky.cfw.inventory.InfoProvider;
import de.cuuky.cfw.inventory.page.AdvancedPageInventory;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public abstract class VaroPageInventory extends AdvancedPageInventory {

    public VaroPageInventory(AdvancedInventoryManager manager, Player player) {
        super(manager, player);
    }

    @Override
    protected List<InfoProvider> getInfoProvider() {
        return Arrays.asList(new VaroInventoryConfigProvider(this));
    }
}