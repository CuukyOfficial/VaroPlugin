package de.cuuky.varo.gui.admin.discordbot.botregister;

import de.cuuky.cfw.inventory.ItemClick;
import de.cuuky.cfw.item.ItemBuilder;
import de.cuuky.varo.Main;
import de.cuuky.varo.bot.discord.register.BotRegister;
import de.cuuky.varo.gui.VaroAsyncListInventory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BotRegisterListGUI extends VaroAsyncListInventory<BotRegister> {

    public BotRegisterListGUI(Player player) {
        super(Main.getCuukyFrameWork().getAdvancedInventoryManager(), player, BotRegister.getBotRegister());
    }

    @Override
    protected ItemStack getItemStack(BotRegister register) {
        return new ItemBuilder().playername(register.getPlayerName())
                .lore("§7Player Name: " + Main.getColorCode() + register.getUUID(),
                        "§7Player Name: " + Main.getColorCode() + register.getPlayerName(),
                        "§7Is Bypassing: " + Main.getColorCode() + register.isBypass(),
                        "§7Discord User: " + Main.getColorCode() + register.getMember().getAsMention()).buildSkull();
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