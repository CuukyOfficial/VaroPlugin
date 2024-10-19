package de.cuuky.varo.gui;

import de.varoplugin.cfw.inventory.AdvancedInventory;
import de.varoplugin.cfw.inventory.AdvancedInventoryManager;
import de.varoplugin.cfw.version.ServerVersion;
import de.varoplugin.cfw.version.VersionUtils;
import de.cuuky.varo.Main;
import org.bukkit.entity.Player;

public abstract class VaroInventory extends AdvancedInventory {

    public VaroInventory(AdvancedInventoryManager manager, Player player) {
        super(manager, player);
    }

    protected void sendInfo() {
        getPlayer().sendMessage(Main.getPrefix() + Main.getColorCode() + "§l" + Main.getPluginName());
        getPlayer().sendMessage(Main.getPrefix() + "§7Version: " + Main.getColorCode() + Main.getInstance().getDescription().getVersion());
        getPlayer().sendMessage(Main.getPrefix() + "§7Discord-Server: " + Main.getColorCode() + Main.DISCORD_INVITE);
        getPlayer().sendMessage(Main.getPrefix() + "§7This software is licensed under the GNU AGPL v3 license");
        getPlayer().sendMessage(Main.getPrefix() + "§7Source code: https://github.com/CuukyOfficial/VaroPlugin");
    }

    public static int getFixedSize(int size) {
        if (VersionUtils.getVersion().isHigherThan(ServerVersion.ONE_8))
            return size < 1 ? 1 : (Math.min(size, 64));
        return size;
    }
}