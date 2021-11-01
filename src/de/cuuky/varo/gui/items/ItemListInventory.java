package de.cuuky.varo.gui.items;

import de.cuuky.cfw.inventory.Info;
import de.cuuky.cfw.inventory.InventoryNotifiable;
import de.cuuky.cfw.inventory.list.AdvancedEditListInventory;
import de.cuuky.cfw.utils.item.BuildItem;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.Main;
import de.cuuky.varo.list.item.ItemList;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public class ItemListInventory extends AdvancedEditListInventory implements InventoryNotifiable {

    private final ItemList list;

    public ItemListInventory(Player player, ItemList list) {
        super(Main.getCuukyFrameWork().getAdvancedInventoryManager(), player, list.getItems());

        this.list = list;
    }

    private boolean hasWritePermission() {
        return getPlayer().hasPermission("varo.item");
    }

    private void save() {
        if (!this.hasWritePermission()) return;
        this.list.getItems().clear();
        this.collectNullFilteredItems().forEach(item -> {
            if (this.list.isUniqueType() && this.list.hasItem(item)) return;
            this.list.addItem(item);
        });
        this.list.saveList();
    }

    @Override
    public int getMaxPage() {
        return this.list.getMaxSize() == -1 ? Integer.MAX_VALUE
                : (int) Math.ceil((double) this.list.getMaxSize() / (double) this.getInfo(Info.SIZE));
    }

    @Override
    protected int getMinPageSize() {
        return this.list.getMaxSize() == -1 ? 54 : this.list.getMaxSize();
    }

    @Override
    public String getTitle() {
        return Main.getColorCode() + list.getLocation();
    }

    @Override
    public void onClose() {
        this.save();
    }

    @Override
    public void refreshContent() {
        super.refreshContent();
        if (this.hasWritePermission()) {
            this.addItem(this.getUsableSize() + 1, new BuildItem().material(Materials.SIGN).displayName("§aTipp!")
                    .lore("", "§7Nur du als Admin kannst diese", "§7Listen bearbeiten!").build());
//            this.addItem(this.getUsableSize() + 7, new BuildItem().material(Materials.EMERALD).displayName("§2Save")
//                    .lore("§7Speichere das Inventar", "§7(Beim Schließen des Inventars wird automatisch gespeichert)")
//                    .build(), (e) -> {
//                this.save();
//                update();
//            });
        }
    }

    @Override
    public void onInventoryDrag(InventoryDragEvent event) {
        if (!this.hasWritePermission()) event.setCancelled(true);
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        if (!this.hasWritePermission()) event.setCancelled(true);
    }
}