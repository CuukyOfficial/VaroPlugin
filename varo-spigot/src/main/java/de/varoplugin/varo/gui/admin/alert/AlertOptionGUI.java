package de.varoplugin.varo.gui.admin.alert;

import org.bukkit.entity.Player;

import com.cryptomorin.xseries.XMaterial;

import de.varoplugin.cfw.item.ItemBuilder;
import de.varoplugin.varo.Main;
import de.varoplugin.varo.alert.Alert;
import de.varoplugin.varo.gui.VaroInventory;

public class AlertOptionGUI extends VaroInventory {

    private Alert alert;

    public AlertOptionGUI(Player player, Alert alert) {
        super(Main.getInventoryManager(), player);

        this.alert = alert;
    }

    @Override
    public String getTitle() {
        return "§7Alert §c" + alert.getId();
    }

    @Override
    public int getSize() {
        return 18;
    }

    @Override
    public void refreshContent() {
        addItem(4, ItemBuilder.material(alert.isOpen() ? XMaterial.REDSTONE : XMaterial.EMERALD).displayName(alert.isOpen() ? "§cClose" : "§aOpen").build(),
                (event) -> alert.switchOpenState());
    }
}