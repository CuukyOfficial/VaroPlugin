package de.cuuky.varo.gui;

import de.cuuky.cfw.inventory.AdvancedInventory;
import de.cuuky.cfw.inventory.AdvancedInventoryManager;
import de.cuuky.cfw.version.BukkitVersion;
import de.cuuky.cfw.version.VersionUtils;
import de.cuuky.varo.Main;
import org.bukkit.entity.Player;

public abstract class VaroInventory extends AdvancedInventory {

    public VaroInventory(AdvancedInventoryManager manager, Player player) {
        super(manager, player);
        this.addProvider(new VaroInventoryConfigProvider(this));
    }

    protected void sendInfo() {
        getPlayer().sendMessage(Main.getPrefix() + Main.getColorCode() + "§l" + Main.getPluginName());
        getPlayer().sendMessage(Main.getPrefix() + "§7Version: " + Main.getColorCode() + Main.getInstance().getDescription().getVersion());
        getPlayer().sendMessage(Main.getPrefix() + "§7Discord-Server: " + Main.getColorCode() + Main.DISCORD_INVITE);
        getPlayer().sendMessage(Main.getPrefix() + "§7All rights reserved!");
    }

    protected int getFixedSize(int size) {
        if (VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_8))
            return (size < 1 ? 1 : (Math.min(size, 64)));
        else
            return size;
    }
}