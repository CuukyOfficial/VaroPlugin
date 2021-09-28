package de.cuuky.varo.gui.admin.customcommands;

import de.cuuky.cfw.inventory.ItemClick;
import de.cuuky.cfw.utils.item.BuildSkull;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.Main;
import de.cuuky.varo.command.custom.CustomCommand;
import de.cuuky.varo.gui.VaroListInventory;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CustomCommandListGUI extends VaroListInventory<CustomCommand> {

    public CustomCommandListGUI(Player player) {
        super(Main.getCuukyFrameWork().getAdvancedInventoryManager(), player, Main.getDataManager().getCustomCommandManager().getCommands());
    }

    @Override
    protected ItemStack getItemStack(CustomCommand customCommand) {
        String[] lore = new String[]{ChatColor.GRAY + "Output: " + Main.getColorCode() + customCommand.getOutput(), ChatColor.GRAY + "Beschreibung: " + Main.getColorCode() + customCommand.getDescription(),
                ChatColor.GRAY + "Permission: " + Main.getColorCode() + customCommand.getPermission(), ChatColor.GRAY + "Unused: " + Main.getColorCode() + customCommand.isUnused()};

        return new BuildSkull().player(customCommand.getName())
                .displayName(Main.getColorCode() + customCommand.getName()).itemstack(Materials.SIGN.parseItem())
                .lore(lore).build();
    }

    @Override
    protected ItemClick getClick(CustomCommand customCommand) {
        return (event) -> this.openNext(new CreateCustomCommandGUI(getPlayer(), customCommand));
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
