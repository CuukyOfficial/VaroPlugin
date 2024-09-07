package de.cuuky.varo.gui.admin.discordbot.botregister;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.Main;
import de.cuuky.varo.bot.discord.register.BotRegister;
import de.cuuky.varo.gui.VaroAsyncListInventory;
import de.varoplugin.cfw.inventory.ItemClick;
import de.varoplugin.cfw.item.ItemBuilder;

public class BotRegisterListGUI extends VaroAsyncListInventory<BotRegister> {

    public BotRegisterListGUI(Player player) {
        super(Main.getInventoryManager(), player, BotRegister.getBotRegister());
    }

    @Override
    protected ItemStack getItemStack(BotRegister register) {
        return ItemBuilder.skull(UUID.fromString(register.getUUID()))
                .lore("§7Player Name: " + Main.getColorCode() + register.getUUID(),
                        "§7Player Name: " + Main.getColorCode() + register.getPlayerName(),
                        "§7Is Bypassing: " + Main.getColorCode() + register.isBypass(),
                        "§7Discord User: " + Main.getColorCode() + register.getMember().getAsMention()).build();
    }

    @Override
    protected ItemClick getClick(BotRegister register) {
        return (event) -> this.openNext(new BotRegisterGUI(getPlayer(), register));
    }

    @Override
    public String getTitle() {
        return "§cBotVerify";
    }

    @Override
    public int getSize() {
        return this.getRecommendedSize();
    }
}