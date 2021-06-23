package de.cuuky.varo.gui.admin.discordbot;

import de.cuuky.cfw.item.ItemBuilder;
import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.gui.VaroInventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DiscordBotGUI extends VaroInventory {

    public DiscordBotGUI(Player player) {
        super(Main.getCuukyFrameWork().getAdvancedInventoryManager(), player);
    }

    @Override
    public String getTitle() {
        return "§2DiscordBot";
    }

    @Override
    public int getSize() {
        return 27;
    }

    @Override
    public void refreshContent() {
        addItem(1, new ItemBuilder().displayname(Main.getBotLauncher().getDiscordbot().isEnabled() ? "§cShutdown" : "§aStart")
                .itemstack(new ItemStack(Main.getBotLauncher().getDiscordbot().isEnabled() ? Material.REDSTONE : Material.EMERALD)).build(), (event) -> {
            boolean enabled = Main.getBotLauncher().getDiscordbot().isEnabled();
            if (enabled)
                Main.getBotLauncher().getDiscordbot().disconnect();
            else
                Main.getBotLauncher().getDiscordbot().connect();

            if (Main.getBotLauncher().getDiscordbot().isEnabled() == enabled)
                getPlayer().sendMessage(Main.getPrefix() + "§7Could not start DiscordBot.");
            else
                getPlayer().sendMessage(Main.getPrefix() + "§7Erfolg!");
        });

        addItem(7, new ItemBuilder().displayname("§eBotRegister").itemstack(new ItemStack(Material.BOOK)).build(), (event) -> {
            if (Main.getBotLauncher().getDiscordbot().isEnabled() || !ConfigSetting.DISCORDBOT_VERIFYSYSTEM.getValueAsBoolean()) {
                getPlayer().sendMessage(Main.getPrefix() + "Das System ist nicht aktiviert!");
                return;
            }
        });
    }
}