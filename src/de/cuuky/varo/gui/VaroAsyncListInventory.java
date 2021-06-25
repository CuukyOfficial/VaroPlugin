package de.cuuky.varo.gui;

import de.cuuky.cfw.hooking.HookManager;
import de.cuuky.cfw.inventory.AdvancedInventoryManager;
import de.cuuky.cfw.inventory.list.AdvancedAsyncListInventory;
import de.cuuky.varo.Main;
import de.cuuky.varo.logger.logger.EventLogger;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class VaroAsyncListInventory<T> extends AdvancedAsyncListInventory<T> {

    public VaroAsyncListInventory(AdvancedInventoryManager manager, Player player, List<T> list) {
        super(manager, player, list);
        this.addProvider(new VaroInventoryConfigProvider(this));
    }

    public VaroAsyncListInventory(AdvancedInventoryManager manager, Player player) {
        super(manager, player);
        this.addProvider(new VaroInventoryConfigProvider(this));
    }

    @Override
    protected HookManager getHookManager() {
        return Main.getCuukyFrameWork().getHookManager();
    }

    @Override
    protected String getEmptyName() {
        if (this.getEmptyClicked() == 200 && getPlayer().hasPermission("varo.setup"))
            Main.getDataManager().getVaroLoggerManager().getEventLogger().println(EventLogger.LogType.LOG, "An admin clicked too often!%noDiscord%");

        return super.getEmptyName();
    }
}