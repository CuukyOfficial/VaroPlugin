package de.cuuky.varo.gui.admin.inventory;

import org.bukkit.entity.Player;

import com.cryptomorin.xseries.XMaterial;

import de.cuuky.varo.Main;
import de.cuuky.varo.entity.player.stats.stat.inventory.InventoryBackup;
import de.cuuky.varo.gui.VaroInventory;
import de.varoplugin.cfw.inventory.inbuilt.ConfirmInventory;
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
            if (!Main.getVaroGame().hasStarted()) {
                getPlayer().sendMessage(Main.getPrefix() + "§cDas Projekt wurde noch nicht gestartet!");
                return;
            }

            if (!backup.getVaroPlayer().isOnline()) {
                backup.getVaroPlayer().getStats().setRestoreBackup(backup);
                getPlayer().sendMessage(Main.getPrefix() + "Inventar wird beim naechsten Betreten wiederhergestellt!");
                return;
            }

            backup.restore(backup.getVaroPlayer().getPlayer());
            getPlayer().sendMessage(Main.getPrefix() + "Inventar wurde wiederhergestellt!");
        });

        addItem(7, ItemBuilder.material(XMaterial.REDSTONE).displayName("§cRemove").build(), (event) -> {
            this.openNext(new ConfirmInventory(this, "§4Remove inventory backup?", (result) -> {
                if (result)
                    this.backup.getVaroPlayer().getStats().removeInventoryBackup(this.backup);
                this.back();
            }));
        });
    }
}