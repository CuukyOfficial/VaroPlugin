package de.varoplugin.varo.gui.events;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.cryptomorin.xseries.XMaterial;

import de.varoplugin.cfw.inventory.ItemClick;
import de.varoplugin.cfw.item.ItemBuilder;
import de.varoplugin.varo.Main;
import de.varoplugin.varo.gui.VaroListInventory;

public class EventListGUI extends VaroListInventory<String> {

    public EventListGUI(Player player) {
        super(Main.getInventoryManager(), player,
            Main.getDataManager().getVaroLoggerManager().getEventLogger().getLogs());
    }

    @Override
    public String getTitle() {
        return "§5Events";
    }

    @Override
    public int getSize() {
        return this.getRecommendedSize();
    }

    @Override
    protected ItemStack getItemStack(String string) {
        String[] line = string.split("] ");
        String lore = "§c" + line[0].replace("[", "");
        return ItemBuilder.material(XMaterial.OAK_SIGN).displayName("§7" + line[1].replaceAll("&[0-9]", "")).lore(lore).build();
    }

    @Override
    protected ItemClick getClick(String s) {
        return null;
    }
}