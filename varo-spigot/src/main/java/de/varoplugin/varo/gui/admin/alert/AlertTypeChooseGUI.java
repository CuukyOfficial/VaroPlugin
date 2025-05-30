package de.varoplugin.varo.gui.admin.alert;

import org.bukkit.entity.Player;

import de.varoplugin.cfw.item.ItemBuilder;
import de.varoplugin.varo.Main;
import de.varoplugin.varo.gui.VaroInventory;
import de.varoplugin.varo.gui.admin.alert.AlertChooseGUI.AlertGUIType;

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