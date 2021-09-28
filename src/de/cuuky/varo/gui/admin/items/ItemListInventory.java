package de.cuuky.varo.gui.admin.items;

import de.cuuky.cfw.inventory.Info;
import de.cuuky.cfw.inventory.InventoryNotifiable;
import de.cuuky.cfw.inventory.list.AdvancedEditListInventory;
import de.cuuky.cfw.version.types.Sounds;
import de.cuuky.varo.Main;
import de.cuuky.varo.list.item.ItemList;
import org.bukkit.entity.Player;

public class ItemListInventory extends AdvancedEditListInventory implements InventoryNotifiable {

    private final ItemList list;

    public ItemListInventory(Player player, ItemList list) {
        super(Main.getCuukyFrameWork().getAdvancedInventoryManager(), player, list.getItems());

        this.list = list;
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
        this.list.getItems().clear();
        this.collectNullFilteredItems().forEach(item -> {
            if (this.list.isUniqueType() && this.list.hasItem(item)) return;
            this.list.addItem(item);
        });
        this.list.saveList();
        this.getPlayer().playSound(this.getPlayer().getLocation(), Sounds.ANVIL_USE.bukkitSound(), 1f, 1f);
    }
}