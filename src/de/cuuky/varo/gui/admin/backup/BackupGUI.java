package de.cuuky.varo.gui.admin.backup;

import de.cuuky.cfw.utils.item.BuildItem;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.app.Main;
import de.cuuky.varo.gui.VaroInventory;
import de.cuuky.varo.recovery.recoveries.VaroBackup;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BackupGUI extends VaroInventory {

    private final VaroBackup backup;

    public BackupGUI(Player opener, VaroBackup backup) {
        super(Main.getCuukyFrameWork().getAdvancedInventoryManager(), opener);

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
        addItem(1, new BuildItem().displayName("§aLoad").itemstack(new ItemStack(Material.EMERALD)).build(), (event) -> {
            if (backup.unzip("plugins/Varo")) {
                this.close();
                getPlayer().sendMessage(Main.getPrefix() + "Backup erfolgreich wieder hergestellt!");
                Main.getDataManager().setDoSave(false);
                Bukkit.getServer().reload();
            } else
                getPlayer().sendMessage(Main.getPrefix() + "Backup konnte nicht wieder hergestellt werden!");
        });

        addItem(7, new BuildItem().displayName("§4Delete").material(Materials.REDSTONE).build(), (event) -> {
            backup.delete();
            this.back();
        });
    }
}