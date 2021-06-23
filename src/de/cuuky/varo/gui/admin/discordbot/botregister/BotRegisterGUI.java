package de.cuuky.varo.gui.admin.discordbot.botregister;

import de.cuuky.cfw.item.ItemBuilder;
import de.cuuky.varo.Main;
import de.cuuky.varo.bot.discord.register.BotRegister;
import de.cuuky.varo.gui.VaroInventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BotRegisterGUI extends VaroInventory {

    private final BotRegister register;

    public BotRegisterGUI(Player player, BotRegister register) {
        super(Main.getCuukyFrameWork().getAdvancedInventoryManager(), player);

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
        addItem(1, new ItemBuilder().displayname("§4Delete").itemstack(new ItemStack(Material.REDSTONE)).build(), (event) -> {
            register.delete();
            this.back();
        });

        addItem(4, new ItemBuilder().displayname("§cUnregister").itemstack(new ItemStack(Material.COAL)).build(),
                (event) -> register.setUserId(-1));

        addItem(7, new ItemBuilder().displayname((register.isBypass() ? "§cRemove" : "§aAllow") + " §7Bypass")
                        .itemstack(new ItemStack(register.isBypass() ? Material.ANVIL : Material.EMERALD)).build(),
                (event) -> register.setBypass(!register.isBypass()));
    }
}