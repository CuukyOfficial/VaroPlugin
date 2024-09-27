package de.cuuky.varo.gui.admin.backup;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.cryptomorin.xseries.XMaterial;

import de.cuuky.varo.Main;
import de.cuuky.varo.gui.VaroListInventory;
import de.cuuky.varo.recovery.recoveries.VaroBackup;
import de.varoplugin.cfw.inventory.ItemClick;
import de.varoplugin.cfw.item.ItemBuilder;

public class BackupListGUI extends VaroListInventory<VaroBackup> {

    public BackupListGUI(Player player) {
        super(Main.getInventoryManager(), player, VaroBackup.getBackups());
    }

    @Override
    protected ItemStack getItemStack(VaroBackup backup) {
        List<String> lore = new ArrayList<>();
        lore.add("Backup made date: ");
        String[] split1 = backup.getZipFile().getName().split("_");
        lore.add("Year: " + split1[0].split("-")[0] + ", Month: " + split1[0].split("-")[1] + ", Day: " + split1[0].split("-")[2]);
        lore.add("Hour: " + split1[1].split("-")[0] + ", Minute: " + split1[1].split("-")[1] + ", Second: " + split1[1].split("-")[2].replace(".zip", ""));
        return ItemBuilder.material(XMaterial.DISPENSER).displayName("§7" + backup.getZipFile().getName().replace(".zip", "")).lore(lore).build();
    }

    @Override
    protected ItemClick getClick(VaroBackup backup) {
        return (event) -> this.openNext(new BackupGUI(getPlayer(), backup));
    }

    @Override
    public void refreshContent() {
        super.refreshContent();
        addItem(this.getSize() - 1, ItemBuilder.material(XMaterial.EMERALD).displayName("§aCreate Backup").build(), (event) -> {
            if (VaroBackup.getBackup(VaroBackup.newFileName()) != null) {
                getPlayer().sendMessage(Main.getPrefix() + "Warte kurz, bevor du ein neues Backup erstellen kannst.");
                return;
            }

            new VaroBackup();
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