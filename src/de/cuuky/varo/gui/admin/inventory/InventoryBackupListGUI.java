package de.cuuky.varo.gui.admin.inventory;

import de.cuuky.cfw.inventory.ItemClick;
import de.cuuky.cfw.item.ItemBuilder;
import de.cuuky.varo.Main;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.stats.stat.inventory.InventoryBackup;
import de.cuuky.varo.gui.VaroListInventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;

public class InventoryBackupListGUI extends VaroListInventory<InventoryBackup> {

    private final VaroPlayer target;

    public InventoryBackupListGUI(Player opener, VaroPlayer target) {
        super(Main.getCuukyFrameWork().getAdvancedInventoryManager(), opener, target.getStats().getInventoryBackups());

        this.target = target;
    }

    @Override
    public String getTitle() {
        return "§7Backups: §b" + target.getName();
    }

    @Override
    public int getSize() {
        return 54;
    }

    @Override
    protected ItemStack getItemStack(InventoryBackup backup) {
        return new ItemBuilder().displayname(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
                .format(backup.getDate())).itemstack(new ItemStack(Material.BOOK)).build();
    }

    @Override
    protected ItemClick getClick(InventoryBackup backup) {
        return (event) -> this.openNext(new InventoryBackupGUI(getPlayer(), backup));
    }

    @Override
    public void refreshContent() {
        super.refreshContent();

        addItem(this.getSize() - 1, new ItemBuilder().displayname("§aCreate Backup").itemstack(new ItemStack(Material.EMERALD)).build(), (event) -> {
            if (!target.isOnline()) {
                getPlayer().sendMessage(Main.getPrefix() + "Dieser Spieler ist nicht online!");
                return;
            }

            target.getStats().addInventoryBackup(new InventoryBackup(target));
        });
    }
}