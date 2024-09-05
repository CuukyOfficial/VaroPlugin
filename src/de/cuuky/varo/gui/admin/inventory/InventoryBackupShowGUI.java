package de.cuuky.varo.gui.admin.inventory;

import de.varoplugin.cfw.inventory.InventoryNotifiable;
import de.varoplugin.cfw.inventory.list.AdvancedEditInventory;
import de.cuuky.cfw.utils.item.BuildItem;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.Main;
import de.cuuky.varo.entity.player.stats.stat.inventory.InventoryBackup;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collection;

public class InventoryBackupShowGUI extends AdvancedEditInventory implements InventoryNotifiable {

    private final InventoryBackup backup;

    public InventoryBackupShowGUI(Player player, InventoryBackup backup) {
        super(Main.getInventoryManager(), player);

        this.backup = backup;
    }

//    @Override
//    protected ItemStack getStack(int i) {
//        ItemStack[] content = this.backup.getAllContents();
//        return content.length <= i ? null : this.backup.getAllContents()[i];
//    }

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
        this.backup.clear();
        for (int i = 0; i < this.backup.getAllContents().length; i++) {
            ItemStack item = getInventory().getItem(i);
            if (item != null) this.backup.setItem(i, item);
        }
    }

    @Override
    public void refreshContent() {
        super.refreshContent();
        this.addItem(this.getUsableSize(), new BuildItem().material(Materials.SIGN).displayName("§aTipp!")
                .lore("§7Du kannst in dem Feld über mir die Rüstung platzieren!", "",
                        "§7Starte dafür über mir mit den Schuhen",
                        "§7und danach jeweils einen nach rechts die",
                        "§7Hose, den Brustpanzer und den Helm!").build());
    }
}