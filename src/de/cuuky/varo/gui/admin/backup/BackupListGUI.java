package de.cuuky.varo.gui.admin.backup;

import de.varoplugin.cfw.inventory.ItemClick;
import de.cuuky.cfw.utils.item.BuildItem;
import de.cuuky.varo.Main;
import de.cuuky.varo.gui.VaroListInventory;
import de.cuuky.varo.recovery.recoveries.VaroBackup;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BackupListGUI extends VaroListInventory<VaroBackup> {

    public BackupListGUI(Player player) {
        super(Main.getInventoryManager(), player, VaroBackup.getBackups());
    }

    private String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        Date date = new Date();

        return dateFormat.format(date);
    }

    @Override
    protected ItemStack getItemStack(VaroBackup backup) {
        List<String> lore = new ArrayList<>();
        lore.add("Backup made date: ");
        String[] split1 = backup.getZipFile().getName().split("_");
        lore.add("Year: " + split1[0].split("-")[0] + ", Month: " + split1[0].split("-")[1] + ", Day: " + split1[0].split("-")[2]);
        lore.add("Hour: " + split1[1].split("-")[0] + ", Minute: " + split1[1].split("-")[1] + ", Second: " + split1[1].split("-")[2].replace(".zip", ""));
        return new BuildItem()
                .displayName("§7" + backup.getZipFile().getName().replace(".zip", ""))
                .itemstack(new ItemStack(Material.DISPENSER)).lore(lore).build();
    }

    @Override
    protected ItemClick getClick(VaroBackup backup) {
        return (event) -> this.openNext(new BackupGUI(getPlayer(), backup));
    }

    @Override
    public void refreshContent() {
        super.refreshContent();
        addItem(this.getSize() - 1, new BuildItem().displayName("§aCreate Backup")
                .itemstack(new ItemStack(Material.EMERALD)).build(), (event) -> {
            if (VaroBackup.getBackup(getCurrentDate()) != null) {
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