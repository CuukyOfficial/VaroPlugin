package de.cuuky.varo.gui.admin.inventory;

import java.util.Arrays;
import java.util.Collection;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.cryptomorin.xseries.XMaterial;

import de.cuuky.varo.Main;
import de.cuuky.varo.player.stats.stat.inventory.InventoryBackup;
import de.varoplugin.cfw.inventory.InventoryNotifiable;
import de.varoplugin.cfw.inventory.list.AdvancedEditInventory;
import de.varoplugin.cfw.item.ItemBuilder;

public class InventoryBackupShowGUI extends AdvancedEditInventory implements InventoryNotifiable {

    private final InventoryBackup backup;

    public InventoryBackupShowGUI(Player player, InventoryBackup backup) {
        super(Main.getInventoryManager(), player);

        this.backup = backup;
    }

    @Override
    public String getTitle() {
        return "§7Inventory: " + Main.getColorCode() + backup.getVaroPlayer().getName();
    }

    @Override
    public int getMaxPage() {
        return 1;
    }

    @Override
    protected int getMinSize() {
        return 54;
    }

    @Override
    protected Collection<ItemStack> getInitialItems() {
        return Arrays.asList(this.backup.getAllContents());
    }

    @Override
    public boolean shallInsertFiller(int location, ItemStack stack) {
        return location >= this.backup.getAllContents().length;
    }

    @Override
    public void onClose() {
        for (int i = 0; i < this.backup.getAllContents().length; i++) {
            ItemStack item = getInventory().getItem(i);
            if (item != null)
                this.backup.setItem(i, item);
            else
                this.backup.setItem(i, new ItemStack(Material.AIR));
        }
    }

    @Override
    public void refreshContent() {
        super.refreshContent();
        this.addItem(this.getUsableSize(), ItemBuilder.material(XMaterial.OAK_SIGN).displayName("§aTipp!")
                .lore("§7Du kannst in dem Feld über mir die Rüstung platzieren!", "",
                        "§7Starte dafür über mir mit den Schuhen",
                        "§7und danach jeweils einen nach rechts die",
                        "§7Hose, den Brustpanzer und den Helm!").build());
        this.addItem(this.getSize() - 1, ItemBuilder.material(XMaterial.EXPERIENCE_BOTTLE).displayName("§2Experience")
                .amount(this.backup.getExpLevel()).build());
    }
}