package de.cuuky.varo.gui.admin.alert;

import org.bukkit.entity.Player;

import com.cryptomorin.xseries.XMaterial;

import de.cuuky.varo.Main;
import de.cuuky.varo.alert.Alert;
import de.cuuky.varo.gui.VaroInventory;
import de.varoplugin.cfw.item.ItemBuilder;

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