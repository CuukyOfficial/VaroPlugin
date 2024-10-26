package de.cuuky.varo.gui.admin.discordbot;

import org.bukkit.entity.Player;

import com.cryptomorin.xseries.XMaterial;

import de.cuuky.varo.Main;
import de.cuuky.varo.bot.discord.BotRegister;
import de.cuuky.varo.gui.VaroInventory;
import de.varoplugin.cfw.item.ItemBuilder;

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
        addItem(1, ItemBuilder.material(XMaterial.REDSTONE).displayName("§4Delete").build(), (event) -> {
            register.delete();
            this.back();
        });

        addItem(4, ItemBuilder.material(XMaterial.COAL).displayName("§cUnregister").build(),
                (event) -> register.setUserId(-1));

        addItem(7, ItemBuilder.material(register.isBypass() ? XMaterial.ANVIL : XMaterial.EMERALD)
                .displayName((register.isBypass() ? "§cRemove" : "§aAllow") + " §7Bypass").build(),
                (event) -> register.setBypass(!register.isBypass()));
    }
}