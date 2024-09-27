package de.cuuky.varo.gui.team;

import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.gui.VaroInventory;
import de.cuuky.varo.gui.team.TeamListGUI.TeamGUIType;
import de.varoplugin.cfw.item.ItemBuilder;

public class TeamCategoryChooseGUI extends VaroInventory {

    public TeamCategoryChooseGUI(Player player) {
        super(Main.getInventoryManager(), player);
    }

    @Override
    public String getTitle() {
        return "§3Choose Category";
    }

    @Override
    public int getSize() {
        return 36;
    }

    @Override
    public void refreshContent() {
        int i = 10;
        for (TeamGUIType type : TeamGUIType.values()) {
            addItem(i, ItemBuilder.material(type.getIcon()).displayName(type.getTypeName()).amount(getFixedSize(type.getList().size())).build(), (event) ->
                    this.openNext(new TeamListGUI(getPlayer(), type))
            );
            i += 2;
        }
    }
}
