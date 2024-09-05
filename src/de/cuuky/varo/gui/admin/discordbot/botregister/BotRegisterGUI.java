package de.cuuky.varo.gui.admin.discordbot.botregister;

import de.cuuky.cfw.utils.item.BuildItem;
import de.cuuky.varo.Main;
import de.cuuky.varo.bot.discord.register.BotRegister;
import de.cuuky.varo.gui.VaroInventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BotRegisterGUI extends VaroInventory {

    private final BotRegister register;

    public BotRegisterGUI(Player player, BotRegister register) {
        super(Main.getInventoryManager(), player);

        this.register = register;
    }

    @Override
    public String getTitle() {
        return "§7BotRegister: §a" + register.getPlayerName();
    }

    @Override
    public int getSize() {
        return 18;
    }

    @Override
    public void refreshContent() {
        addItem(1, new BuildItem().displayName("§4Delete").itemstack(new ItemStack(Material.REDSTONE)).build(), (event) -> {
            register.delete();
            this.back();
        });

        addItem(4, new BuildItem().displayName("§cUnregister").itemstack(new ItemStack(Material.COAL)).build(),
                (event) -> register.setUserId(-1));

        addItem(7, new BuildItem().displayName((register.isBypass() ? "§cRemove" : "§aAllow") + " §7Bypass")
                        .itemstack(new ItemStack(register.isBypass() ? Material.ANVIL : Material.EMERALD)).build(),
                (event) -> register.setBypass(!register.isBypass()));
    }
}