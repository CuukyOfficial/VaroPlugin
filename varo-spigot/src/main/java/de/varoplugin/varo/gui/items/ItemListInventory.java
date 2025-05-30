package de.varoplugin.varo.gui.items;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

import com.cryptomorin.xseries.XMaterial;

import de.varoplugin.cfw.inventory.Info;
import de.varoplugin.cfw.inventory.InventoryNotifiable;
import de.varoplugin.cfw.inventory.list.AdvancedEditListInventory;
import de.varoplugin.cfw.item.ItemBuilder;
import de.varoplugin.varo.Main;
import de.varoplugin.varo.list.item.ItemList;

public class ItemListInventory extends AdvancedEditListInventory implements InventoryNotifiable {

    private final ItemList list;

    public ItemListInventory(Player player, ItemList list) {
        super(Main.getInventoryManager(), player, list.getItems());

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
    protected int getMinSize() {
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
            this.addItem(this.getUsableSize() + 1, ItemBuilder.material(XMaterial.OAK_SIGN).displayName("§aTipp!")
                    .lore("", "§7Nur du als Admin kannst diese", "§7Listen bearbeiten!").build());
//            this.addItem(this.getUsableSize() + 7, ItemBuilder.material(XMaterial.EMERALD).displayName("§2Save")
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