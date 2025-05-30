package de.cuuky.varo.gui.admin.customcommands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.cryptomorin.xseries.XMaterial;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.custom.CustomCommand;
import de.cuuky.varo.gui.VaroListInventory;
import de.varoplugin.cfw.inventory.ItemClick;
import de.varoplugin.cfw.item.ItemBuilder;

public class CustomCommandListGUI extends VaroListInventory<CustomCommand> {

    public CustomCommandListGUI(Player player) {
        super(Main.getInventoryManager(), player, Main.getDataManager().getCustomCommandManager().getCommands());
    }

    @Override
    protected ItemStack getItemStack(CustomCommand customCommand) {
        String[] lore = new String[]{ChatColor.GRAY + "Output: " + Main.getColorCode() + customCommand.getOutput(), ChatColor.GRAY + "Beschreibung: " + Main.getColorCode() + customCommand.getDescription(),
                ChatColor.GRAY + "Permission: " + Main.getColorCode() + customCommand.getPermission(), ChatColor.GRAY + "Unused: " + Main.getColorCode() + customCommand.isUnused()};

        return ItemBuilder.material(XMaterial.OAK_SIGN).displayName(Main.getColorCode() + customCommand.getName()).lore(lore).build();
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
