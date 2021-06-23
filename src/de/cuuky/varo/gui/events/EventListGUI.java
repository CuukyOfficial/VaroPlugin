package de.cuuky.varo.gui.events;

import de.cuuky.cfw.inventory.ItemClick;
import de.cuuky.cfw.item.ItemBuilder;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.Main;
import de.cuuky.varo.gui.VaroListInventory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EventListGUI extends VaroListInventory<String> {

    public EventListGUI(Player player) {
        super(Main.getCuukyFrameWork().getAdvancedInventoryManager(), player, Main.getDataManager().getVaroLoggerManager().getEventLogger().getLogs());
    }

    @Override
    public String getTitle() {
        return "§5Events";
    }

    @Override
    public int getSize() {
        return 54;
    }

    @Override
    protected ItemStack getItemStack(String string) {
        String[] line = string.split("] ");
        String lore = "§c" + line[0].replace("[", "");
        return new ItemBuilder().displayname("§7" + line[1])
                .itemstack(new ItemStack(Materials.SIGN.parseMaterial())).lore(lore).build();
    }

    @Override
    protected ItemClick getClick(String s) {
        return null;
    }
}