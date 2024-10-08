package de.cuuky.varo.gui.admin.alert;

import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.gui.VaroInventory;
import de.cuuky.varo.gui.admin.alert.AlertChooseGUI.AlertGUIType;
import de.varoplugin.cfw.item.ItemBuilder;

public class AlertTypeChooseGUI extends VaroInventory {

    public AlertTypeChooseGUI(Player opener) {
        super(Main.getInventoryManager(), opener);
    }

    @Override
    public String getTitle() {
        return "§eChoose Alert";
    }

    @Override
    public int getSize() {
        return 36;
    }

    @Override
    public void refreshContent() {
        int i = 11;
        for (AlertGUIType type : AlertGUIType.values()) {
            addItem(i, ItemBuilder.material(type.getIcon()).displayName(type.getTypeName()).amount(getFixedSize(type.getList().size())).build(),
                    (event) -> this.openNext(new AlertChooseGUI(getPlayer(), type)));
            i += 2;
        }
    }
}