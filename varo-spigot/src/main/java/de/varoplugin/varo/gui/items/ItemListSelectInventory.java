package de.varoplugin.varo.gui.items;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.cryptomorin.xseries.XMaterial;

import de.varoplugin.cfw.inventory.ItemClick;
import de.varoplugin.cfw.item.ItemBuilder;
import de.varoplugin.varo.Main;
import de.varoplugin.varo.gui.VaroListInventory;
import de.varoplugin.varo.list.item.ItemList;

public class ItemListSelectInventory extends VaroListInventory<ItemList> {

    public ItemListSelectInventory(Player player) {
        super(Main.getInventoryManager(), player, ItemList.getItemLists());
    }

    private boolean hasWritePermission() {
        return getPlayer().hasPermission("varo.item");
    }

    @Override
    protected ItemStack getItemStack(ItemList itemList) {
        return ItemBuilder.material(XMaterial.CHEST).displayName(Main.getColorCode() + itemList.getLocation())
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