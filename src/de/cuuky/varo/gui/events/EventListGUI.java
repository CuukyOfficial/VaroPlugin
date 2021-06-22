package de.cuuky.varo.gui.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.cfw.item.ItemBuilder;
import de.cuuky.cfw.menu.utils.PageAction;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.Main;
import de.cuuky.varo.gui.MainMenu;
import de.cuuky.varo.gui.VaroSuperInventory;

public class EventListGUI extends VaroSuperInventory {

    public EventListGUI(Player opener) {
        super("§5Events", opener, 54, false);

        this.setModifier = true;
        Main.getCuukyFrameWork().getInventoryManager().registerInventory(this);
        open();
    }

    @Override
    public boolean onBackClick() {
        new MainMenu(opener);
        return true;
    }

    @Override
    public void onClick(InventoryClickEvent event) {
    }

    @Override
    public void onClose(InventoryCloseEvent event) {
    }

    @Override
    public void onInventoryAction(PageAction action) {
    }

    @Override
    public boolean onOpen() {
        List<String> list = Main.getDataManager().getVaroLoggerManager().getEventLogger().getLogs();

        int start = getSize() * (getPage() - 1);
        for (int i = 0; i != getSize(); i++) {
            String[] line;
            try {
                line = list.get(list.size() - 1 - start).split("] ");
            } catch (IndexOutOfBoundsException e) {
                break;
            }

            line[0] = line[0].replace("[", "");
            ArrayList<String> s = new ArrayList<>();
            s.add("§c" + line[0]);
            linkItemTo(i, new ItemBuilder().displayname("§7" + line[1]).itemstack(new ItemStack(Materials.SIGN.parseMaterial())).lore(s).build(), () -> {
            });
            start++;
        }

        return calculatePages(list.size(), getSize()) == page;
    }
}
