package de.cuuky.varo.gui.items;

import de.varoplugin.cfw.inventory.ItemClick;
import de.cuuky.cfw.utils.item.BuildItem;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.Main;
import de.cuuky.varo.gui.VaroListInventory;
import de.cuuky.varo.list.item.ItemList;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemListSelectInventory extends VaroListInventory<ItemList> {

    public ItemListSelectInventory(Player player) {
        super(Main.getInventoryManager(), player, ItemList.getItemLists());
    }

    private boolean hasWritePermission() {
        return getPlayer().hasPermission("varo.item");
    }

    @Override
    protected ItemStack getItemStack(ItemList itemList) {
        return new BuildItem().material(Materials.CHEST).displayName(Main.getColorCode() + itemList.getLocation())
                .lore(this.hasWritePermission() && !itemList.isPublic() ? "§7Nur für Admins einsehbar" : null).build();
    }

//    @Override
//    protected int getMinPageSize() {
//        return ItemList.getItemLists().size();
//    }

    @Override
    public String getTitle() {
        return Main.getColorCode() + "ItemLists";
    }

    @Override
    protected ItemClick getClick(ItemList itemList) {
        return (e) -> {
            if (itemList.isPublic() || this.hasWritePermission())
                this.openNext(new ItemListInventory(this.getPlayer(), itemList));
        };
    }
}