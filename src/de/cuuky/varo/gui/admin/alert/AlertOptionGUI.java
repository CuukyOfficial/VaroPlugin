package de.cuuky.varo.gui.admin.alert;

import de.cuuky.cfw.utils.item.BuildItem;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.Main;
import de.cuuky.varo.alert.Alert;
import de.cuuky.varo.gui.VaroInventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AlertOptionGUI extends VaroInventory {

    private Alert alert;

    public AlertOptionGUI(Player player, Alert alert) {
        super(Main.getCuukyFrameWork().getAdvancedInventoryManager(), player);

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
        addItem(4, new BuildItem().displayName(alert.isOpen() ? "§cClose" : "§aOpen")
                        .itemstack(new ItemStack(alert.isOpen() ? Materials.REDSTONE.parseMaterial() : Material.EMERALD)).build(),
                (event) -> alert.switchOpenState());
    }
}