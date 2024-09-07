package de.cuuky.varo.gui.admin.backup;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.cryptomorin.xseries.XMaterial;

import de.cuuky.varo.Main;
import de.cuuky.varo.gui.VaroInventory;
import de.cuuky.varo.recovery.recoveries.VaroBackup;
import de.varoplugin.cfw.item.ItemBuilder;

public class BackupGUI extends VaroInventory {

    private final VaroBackup backup;

    public BackupGUI(Player opener, VaroBackup backup) {
        super(Main.getInventoryManager(), opener);

        this.backup = backup;
    }

    @Override
    public String getTitle() {
        return "§7Backup §a" + backup.getZipFile().getName().replace(".zip", "");
    }

    @Override
    public int getSize() {
        return 18;
    }

    @Override
    public void refreshContent() {
        addItem(1, ItemBuilder.material(XMaterial.EMERALD).displayName("§aLoad").build(), (event) -> {
            if (backup.unzip("plugins/Varo")) {
                this.close();
                getPlayer().sendMessage(Main.getPrefix() + "Backup erfolgreich wieder hergestellt!");
                Main.getDataManager().setDoSave(false);
                Bukkit.getServer().reload();
            } else
                getPlayer().sendMessage(Main.getPrefix() + "Backup konnte nicht wieder hergestellt werden!");
        });

        addItem(7, ItemBuilder.material(XMaterial.REDSTONE).displayName("§4Delete").build(), (event) -> {
            backup.delete();
            this.back();
        });
    }
}