package de.cuuky.varo.gui.admin.customcommands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.cryptomorin.xseries.XMaterial;

import de.cuuky.varo.Main;
import de.cuuky.varo.gui.VaroInventory;
import de.varoplugin.cfw.item.ItemBuilder;

public class CustomCommandMenuGUI extends VaroInventory {
    public CustomCommandMenuGUI(Player player) {
        super(Main.getInventoryManager(), player);
    }

    @Override
    public String getTitle() { return "Custom Command Menu"; }

    @Override
    public int getSize() { return 36; }

    @Override
    public void refreshContent() {
        addItem(11, ItemBuilder.material(XMaterial.BOOK).displayName(ChatColor.DARK_PURPLE + "Custom Commands").build(), (event) ->
                this.openNext(new CustomCommandListGUI(getPlayer()))
        );

        addItem(15, ItemBuilder.material(XMaterial.EMERALD).displayName(ChatColor.GREEN + "Create Command").build(), (event) ->
                this.openNext(new CreateCustomCommandGUI(getPlayer()))
        );
    }
}
