package de.cuuky.varo.gui.player;

import de.cuuky.cfw.item.ItemBuilder;
import de.cuuky.varo.Main;
import de.cuuky.varo.gui.VaroInventory;
import de.cuuky.varo.gui.player.PlayerListGUI.PlayerGUIType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerListChooseGUI extends VaroInventory {

    public PlayerListChooseGUI(Player player) {
        super(Main.getCuukyFrameWork().getAdvancedInventoryManager(), player);
    }

    @Override
    public String getTitle() {
        return "§cChoose player";
    }

    @Override
    public int getSize() {
        return 18;
    }

    @Override
    public void refreshContent() {
        int i = 0;
        for (PlayerGUIType type : PlayerGUIType.values()) {
            addItem(i, new ItemBuilder().displayname(type.getTypeName()).itemstack(new ItemStack(type.getIcon()))
                    .amount(getFixedSize(type.getList().size())).build(), (event) ->
                    this.openNext(new PlayerListGUI(getPlayer(), type)));
            i += 2;
        }
    }
}