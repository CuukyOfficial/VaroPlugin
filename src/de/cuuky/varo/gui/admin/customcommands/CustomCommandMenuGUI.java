package de.cuuky.varo.gui.admin.customcommands;

import de.cuuky.cfw.utils.item.BuildItem;
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
        addItem(11, new BuildItem().displayName(ChatColor.DARK_PURPLE + "Custom Commands")
                .material(Materials.BOOK).build(), (event) ->
                this.openNext(new CustomCommandListGUI(getPlayer()))
        );

        addItem(15, new BuildItem().displayName(ChatColor.GREEN + "Create Command")
                .material(Materials.EMERALD).build(), (event) ->
                this.openNext(new CreateCustomCommandGUI(getPlayer()))
        );
    }
}
