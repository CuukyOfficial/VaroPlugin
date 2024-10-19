package de.cuuky.varo.gui;

import org.bukkit.entity.Player;

import de.varoplugin.cfw.inventory.AdvancedInventory;
import de.varoplugin.cfw.inventory.AdvancedInventoryManager;
import de.varoplugin.cfw.version.ServerVersion;
import de.varoplugin.cfw.version.VersionUtils;

public abstract class VaroInventory extends AdvancedInventory {

    public VaroInventory(AdvancedInventoryManager manager, Player player) {
        super(manager, player);
    }

    public static int getFixedSize(int size) {
        if (VersionUtils.getVersion().isHigherThan(ServerVersion.ONE_8))
            return size < 1 ? 1 : (Math.min(size, 64));
        return size;
    }
}