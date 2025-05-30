package de.varoplugin.varo.gui.admin.backup;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.cryptomorin.xseries.XMaterial;

import de.varoplugin.cfw.inventory.ItemClick;
import de.varoplugin.cfw.item.ItemBuilder;
import de.varoplugin.varo.Main;
import de.varoplugin.varo.data.VaroBackup;
import de.varoplugin.varo.gui.VaroListInventory;

public class BackupListGUI extends VaroListInventory<VaroBackup> {

    public BackupListGUI(Player player) {
        super(Main.getInventoryManager(), player, Main.getDataManager().getBackups());
    }

    @Override
    protected ItemStack getItemStack(VaroBackup backup) {
        // TODO
//        List<String> lore = new ArrayList<>();
//        lore.add("Backup made date: ");
//        String[] split1 = backup.getZipFile().getName().split("_");
//        lore.add("Year: " + split1[0].split("-")[0] + ", Month: " + split1[0].split("-")[1] + ", Day: " + split1[0].split("-")[2]);
//        lore.add("Hour: " + split1[1].split("-")[0] + ", Minute: " + split1[1].split("-")[1] + ", Second: " + split1[1].split("-")[2].replace(".zip", ""));
//        return ItemBuilder.material(XMaterial.DISPENSER).displayName("§7" + backup.getDisplayName()).lore(lore).build();
        return ItemBuilder.material(XMaterial.DISPENSER).displayName("§7" + backup.getDisplayName()).build();
    }

    @Override
    protected ItemClick getClick(VaroBackup backup) {
        return (event) -> this.openNext(new BackupGUI(getPlayer(), backup));
    }

    @Override
    public void refreshContent() {
        super.refreshContent();
        addItem(this.getSize() - 1, ItemBuilder.material(XMaterial.EMERALD).displayName("§aCreate Backup").build(), (event) -> {
            this.close();
            getPlayer().sendMessage(Main.getPrefix() + "Backup wird erstellt...");
            Main.getDataManager().createBackup(backup -> {
                if (backup != null)
                    getPlayer().sendMessage(Main.getPrefix() + "Backup " + backup.getName() + " wurde erstellt");
                else
                    getPlayer().sendMessage(Main.getPrefix() + "§cFehler beim erstellen des Backups");
            });
        });
    }

    @Override
    public int getSize() {
        return this.getRecommendedSize();
    }


    @Override
    public String getTitle() {
        return "§aBackups";
    }
}