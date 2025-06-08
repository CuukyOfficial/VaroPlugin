package de.varoplugin.varo.gui.admin.inventory;

import java.text.SimpleDateFormat;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.cryptomorin.xseries.XMaterial;

import de.varoplugin.cfw.inventory.ItemClick;
import de.varoplugin.cfw.item.ItemBuilder;
import de.varoplugin.varo.Main;
import de.varoplugin.varo.gui.VaroListInventory;
import de.varoplugin.varo.player.VaroPlayer;
import de.varoplugin.varo.player.stats.stat.inventory.InventoryBackup;

public class InventoryBackupListGUI extends VaroListInventory<InventoryBackup> {

    private final VaroPlayer target;

    public InventoryBackupListGUI(Player opener, VaroPlayer target) {
        super(Main.getInventoryManager(), opener, target.getStats().getInventoryBackups());

        this.target = target;
    }

    @Override
    public String getTitle() {
        return "§7Backups " + Main.getColorCode() + target.getName();
    }

    @Override
    public int getSize() {
        return this.getRecommendedSize();
    }

    @Override
    protected ItemStack getItemStack(int index, InventoryBackup backup) {
        return ItemBuilder.material(XMaterial.BOOK).displayName("§3" + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
                .format(backup.getDate())).build();
    }

    @Override
    protected ItemClick getClick(InventoryBackup backup) {
        return (event) -> this.openNext(new InventoryBackupGUI(getPlayer(), backup));
    }

    @Override
    public void refreshContent() {
        super.refreshContent();

        addItem(this.getSize() - 2, ItemBuilder.material(XMaterial.EMERALD).displayName("§aCreate Backup").build(), (event) -> {
            if (!target.isOnline()) {
                getPlayer().sendMessage(Main.getPrefix() + "Dieser Spieler ist nicht online!");
                return;
            }

            target.getStats().addInventoryBackup(new InventoryBackup(target));
        });
    }
}