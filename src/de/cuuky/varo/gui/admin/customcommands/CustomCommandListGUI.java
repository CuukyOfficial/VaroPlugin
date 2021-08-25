package de.cuuky.varo.gui.admin.customcommands;

import de.cuuky.cfw.inventory.ItemClick;
import de.cuuky.cfw.item.ItemBuilder;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.Main;
import de.cuuky.varo.command.custom.CustomCommand;
import de.cuuky.varo.gui.VaroListInventory;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CustomCommandListGUI extends VaroListInventory<CustomCommand> {

    public CustomCommandListGUI(Player player) {
        super(Main.getCuukyFrameWork().getAdvancedInventoryManager(), player);
    }

    @Override
    protected ItemStack getItemStack(CustomCommand customCommand) {
        String[] lore = new String[]{ChatColor.GRAY + "Output: " + Main.getColorCode() + customCommand.getOutput(), ChatColor.GRAY + "Beschreibung: " + Main.getColorCode() + customCommand.getDescription(),
                ChatColor.GRAY + "Permission: " + Main.getColorCode() + customCommand.getPermission(), ChatColor.GRAY + "Unused: " + Main.getColorCode() + customCommand.isUnused()};

        return new ItemBuilder().displayname(Main.getColorCode() + customCommand.getName())
                .playername(customCommand.getName()).itemstack(Materials.SIGN.parseItem())
                .lore(lore).build();
    }

    @Override
    protected ItemClick getClick(CustomCommand customCommand) {
        return (event) -> this.openNext(new CreateCustomCommandGUI(getPlayer(), customCommand));
    }

    @Override
    public List<CustomCommand> getList() {
        return Main.getDataManager().getCustomCommandManager().getCommands();
    }

    @Override
    public String getTitle() {
        return "§7Custom Commands";
    }

    @Override
    public int getSize() {
        return this.getRecommendedSize();
    }
}
