package de.cuuky.varo.gui.player;

import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.gui.VaroInventory;
import de.cuuky.varo.gui.player.PlayerListGUI.PlayerGUIType;
import de.varoplugin.cfw.item.ItemBuilder;

public class PlayerListChooseGUI extends VaroInventory {

    public PlayerListChooseGUI(Player player) {
        super(Main.getInventoryManager(), player);
    }

    @Override
    public String getTitle() {
        return "§cChoose Player";
    }

    @Override
    public int getSize() {
        return 36;
    }

    @Override
    public void refreshContent() {
        int i = 9;
        for (PlayerGUIType type : PlayerGUIType.values()) {
            addItem(i, ItemBuilder.material(type.getIcon()).displayName(type.getTypeName())
                    .amount(getFixedSize(type.getList().size())).build(), (event) ->
                    this.openNext(new PlayerListGUI(getPlayer(), type)));
            i += 2;
        }
    }
}