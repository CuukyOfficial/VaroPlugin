package de.cuuky.varo.gui.admin.inventory;

import de.cuuky.cfw.inventory.InventoryNotifiable;
import de.cuuky.cfw.inventory.list.AdvancedEditInventory;
import de.cuuky.cfw.utils.item.BuildItem;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.cfw.version.types.Sounds;
import de.cuuky.varo.Main;
import de.cuuky.varo.entity.player.stats.stat.inventory.InventoryBackup;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InventoryBackupShowGUI extends AdvancedEditInventory implements InventoryNotifiable {

    private final InventoryBackup backup;

    public InventoryBackupShowGUI(Player player, InventoryBackup backup) {
        super(Main.getCuukyFrameWork().getAdvancedInventoryManager(), player);

        this.backup = backup;
    }

    @Override
    protected ItemStack getStack(int i) {
        ItemStack[] content = this.backup.getAllContents();
        return content.length <= i ? null : this.backup.getAllContents()[i];
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
    public int getSize() {
        return 54;
    }

    @Override
    public void onClose() {
        this.backup.clear();

        ItemStack[] stack = this.collectItems();
        for (int i = 0; i < 40; i++) {
            ItemStack item = stack[i];
            if (item != null) this.backup.setItem(i, item);
        }
        this.getPlayer().playSound(this.getPlayer().getLocation(), Sounds.ANVIL_USE.bukkitSound(), 1f, 1f);
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