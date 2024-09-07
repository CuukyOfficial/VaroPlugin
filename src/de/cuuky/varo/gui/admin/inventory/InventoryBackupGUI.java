package de.cuuky.varo.gui.admin.inventory;

import org.bukkit.entity.Player;

import com.cryptomorin.xseries.XMaterial;

import de.cuuky.varo.Main;
import de.cuuky.varo.entity.player.stats.stat.inventory.InventoryBackup;
import de.cuuky.varo.gui.VaroInventory;
import de.varoplugin.cfw.item.ItemBuilder;

public class InventoryBackupGUI extends VaroInventory {

    private final InventoryBackup backup;

    public InventoryBackupGUI(Player player, InventoryBackup backup) {
        super(Main.getInventoryManager(), player);

        this.backup = backup;
    }

    @Override
    public String getTitle() {
        return Main.getColorCode() + backup.getVaroPlayer().getName();
    }

    @Override
    public int getSize() {
        return 18;
    }

    @Override
    public void refreshContent() {
        addItem(1, ItemBuilder.material(XMaterial.CHEST).displayName("§aShow").build(),
                (event) -> this.openNext(new InventoryBackupShowGUI(getPlayer(), backup)));

        addItem(4, ItemBuilder.material(XMaterial.EMERALD).displayName("§2Restore").build(), (event) -> {
            if (!backup.getVaroPlayer().isOnline()) {
                backup.getVaroPlayer().getStats().setRestoreBackup(backup);
                getPlayer().sendMessage(Main.getPrefix() + "Inventar wird beim naechsten Betreten wiederhergestellt!");
                return;
            }

            backup.restoreUpdate(backup.getVaroPlayer().getPlayer());
            getPlayer().sendMessage(Main.getPrefix() + "Inventar wurde wiederhergestellt!");
        });

        addItem(7, ItemBuilder.material(XMaterial.REDSTONE).displayName("§cRemove").build(), (event) -> {
            backup.getVaroPlayer().getStats().removeInventoryBackup(backup);
            this.back();
        });
    }
}