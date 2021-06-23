package de.cuuky.varo.gui.team;

import de.cuuky.cfw.item.ItemBuilder;
import de.cuuky.varo.Main;
import de.cuuky.varo.gui.VaroInventory;
import de.cuuky.varo.gui.team.TeamListGUI.TeamGUIType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TeamChooseGUI extends VaroInventory {

    public TeamChooseGUI(Player player) {
        super(Main.getCuukyFrameWork().getAdvancedInventoryManager(), player);
    }

    @Override
    public String getTitle() {
        return "§3Choose Category";
    }

    @Override
    public int getSize() {
        return 18;
    }

    @Override
    public void refreshContent() {
        int i = 1;
        for (TeamGUIType type : TeamGUIType.values()) {
            addItem(i, new ItemBuilder().displayname(type.getTypeName())
                    .itemstack(new ItemStack(type.getIcon())).amount(getFixedSize(type.getList().size())).build(), (event) ->
                    this.openNext(new TeamListGUI(getPlayer(), type))
            );
            i += 2;
        }
    }
}
