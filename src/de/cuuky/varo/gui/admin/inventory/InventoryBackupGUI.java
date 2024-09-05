package de.cuuky.varo.gui.admin.inventory;

import de.cuuky.cfw.utils.item.BuildItem;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.Main;
import de.cuuky.varo.entity.player.stats.stat.inventory.InventoryBackup;
import de.cuuky.varo.gui.VaroInventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
        addItem(1, new BuildItem().displayName("§aShow").itemstack(new ItemStack(Material.CHEST)).build(),
                (event) -> this.openNext(new InventoryBackupShowGUI(getPlayer(), backup)));

        addItem(4, new BuildItem().displayName("§2Restore").itemstack(new ItemStack(Material.EMERALD)).build(), (event) -> {
            if (!backup.getVaroPlayer().isOnline()) {
                backup.getVaroPlayer().getStats().setRestoreBackup(backup);
                getPlayer().sendMessage(Main.getPrefix() + "Inventar wird beim naechsten Betreten wiederhergestellt!");
                return;
            }

            backup.restoreUpdate(backup.getVaroPlayer().getPlayer());
            getPlayer().sendMessage(Main.getPrefix() + "Inventar wurde wiederhergestellt!");
        });

        addItem(7, new BuildItem().displayName("§cRemove").material(Materials.REDSTONE).build(), (event) -> {
            backup.getVaroPlayer().getStats().removeInventoryBackup(backup);
            this.back();
        });
    }
}