package de.cuuky.varo.gui.admin.backup;

import org.bukkit.entity.Player;

import com.cryptomorin.xseries.XMaterial;

import de.cuuky.varo.Main;
import de.cuuky.varo.data.VaroBackup;
import de.cuuky.varo.gui.VaroInventory;
import de.varoplugin.cfw.item.ItemBuilder;

public class BackupGUI extends VaroInventory {

    private final VaroBackup backup;

    public BackupGUI(Player opener, VaroBackup backup) {
        super(Main.getInventoryManager(), opener);

        this.backup = backup;
    }

    @Override
    public String getTitle() {
        return "§7Backup §a" + this.backup.getDisplayName();
    }

    @Override
    public int getSize() {
        return 18;
    }

    @Override
    public void refreshContent() {
        // TODO add confirmation
        addItem(1, ItemBuilder.material(XMaterial.EMERALD).displayName("§aLoad").build(), (event) -> {
            this.close();
            Main.getDataManager().restoreBackup(this.backup);
        });

        addItem(7, ItemBuilder.material(XMaterial.REDSTONE).displayName("§4Delete").build(), (event) -> {
            Main.getDataManager().deleteBackup(this.backup);
            this.back();
        });
    }
}