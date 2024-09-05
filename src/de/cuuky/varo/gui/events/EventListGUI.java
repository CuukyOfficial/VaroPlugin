package de.cuuky.varo.gui.events;

import de.varoplugin.cfw.inventory.ItemClick;
import de.cuuky.cfw.utils.item.BuildItem;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.Main;
import de.cuuky.varo.gui.VaroListInventory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
        return new BuildItem().displayName("§7" + line[1].replaceAll("&[0-9]", ""))
            .material(Materials.SIGN).lore(lore).build();
    }

    @Override
    protected ItemClick getClick(String s) {
        return null;
    }
}