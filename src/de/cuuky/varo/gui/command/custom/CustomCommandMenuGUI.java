package de.cuuky.varo.gui.command.custom;

import de.cuuky.cfw.item.ItemBuilder;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.Main;
import de.cuuky.varo.gui.VaroInventory;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CustomCommandMenuGUI extends VaroInventory {
    public CustomCommandMenuGUI(Player player) {
        super(Main.getCuukyFrameWork().getAdvancedInventoryManager(), player);
    }

    @Override
    public String getTitle() { return "Custom Command Menu"; }

    @Override
    public int getSize() { return 36; }

    @Override
    public void refreshContent() {
        addItem(11, new ItemBuilder().displayname(ChatColor.DARK_PURPLE + "Custom Commands")
                .itemstack(Materials.BOOK.parseItem()).build(), (event) ->
                this.openNext(new CustomCommandListGUI(getPlayer()))
        );

        addItem(15, new ItemBuilder().displayname(ChatColor.GREEN + "Create Command")
                .itemstack(Materials.EMERALD.parseItem()).build(), (event) ->
                this.openNext(new CreateCustomCommandGUI(getPlayer()))
        );
    }
}
