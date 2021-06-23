package de.cuuky.varo.gui.admin.inventory;

import de.cuuky.cfw.item.ItemBuilder;
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
        super(Main.getCuukyFrameWork().getAdvancedInventoryManager(), player);

        this.backup = backup;
    }

    @Override
    public String getTitle() {
        return "§b" + backup.getVaroPlayer().getName();
    }

    @Override
    public int getSize() {
        return 18;
    }

    @Override
    public void refreshContent() {
        addItem(1, new ItemBuilder().displayname("§aShow").itemstack(new ItemStack(Material.CHEST)).build(),
                (event) -> this.openNext(new InventoryBackupShowGUI(getPlayer(), backup)));

        addItem(4, new ItemBuilder().displayname("§2Restore").itemstack(new ItemStack(Material.EMERALD)).build(), (event) -> {
            if (!backup.getVaroPlayer().isOnline()) {
                backup.getVaroPlayer().getStats().setRestoreBackup(backup);
                getPlayer().sendMessage(Main.getPrefix() + "Inventar wird beim naechsten Betreten wiederhergestellt!");
                return;
            }

            backup.restoreUpdate(backup.getVaroPlayer().getPlayer());
            getPlayer().sendMessage(Main.getPrefix() + "Inventar wurde wiederhergestellt!");
        });

        addItem(7, new ItemBuilder().displayname("§cRemove").itemstack(Materials.REDSTONE.parseItem()).build(), (event) -> {
            backup.getVaroPlayer().getStats().removeInventoryBackup(backup);
            this.openNext(new InventoryBackupListGUI(getPlayer(), backup.getVaroPlayer()));
        });
    }
}