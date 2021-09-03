package de.cuuky.varo.gui.admin.alert;

import de.cuuky.cfw.utils.item.BuildItem;
import de.cuuky.varo.Main;
import de.cuuky.varo.gui.VaroInventory;
import de.cuuky.varo.gui.admin.alert.AlertChooseGUI.AlertGUIType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AlertTypeChooseGUI extends VaroInventory {

    public AlertTypeChooseGUI(Player opener) {
        super(Main.getCuukyFrameWork().getAdvancedInventoryManager(), opener);
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
            addItem(i, new BuildItem().displayName(type.getTypeName())
                            .itemstack(new ItemStack(type.getIcon())).amount(getFixedSize(type.getList().size())).build(),
                    (event) -> this.openNext(new AlertChooseGUI(getPlayer(), type)));
            i += 2;
        }
    }
}