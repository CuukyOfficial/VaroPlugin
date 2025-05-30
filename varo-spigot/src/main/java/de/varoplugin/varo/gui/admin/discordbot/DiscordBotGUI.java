package de.varoplugin.varo.gui.admin.discordbot;

import org.bukkit.entity.Player;

import com.cryptomorin.xseries.XMaterial;

import de.varoplugin.cfw.item.ItemBuilder;
import de.varoplugin.varo.Main;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;
import de.varoplugin.varo.gui.VaroInventory;

public class DiscordBotGUI extends VaroInventory {

    public DiscordBotGUI(Player player) {
        super(Main.getInventoryManager(), player);
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
        addItem(1, ItemBuilder.material(Main.getBotLauncher().getDiscordbot().isEnabled() ? XMaterial.REDSTONE : XMaterial.EMERALD)
                .displayName(Main.getBotLauncher().getDiscordbot().isEnabled() ? "§cShutdown" : "§aStart").build(), (event) -> {
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

        addItem(7, ItemBuilder.material(XMaterial.BOOK).displayName("§eBotRegister").build(), (event) -> {
            if (Main.getBotLauncher().getDiscordbot().isEnabled() || !ConfigSetting.DISCORDBOT_VERIFYSYSTEM.getValueAsBoolean()) {
                getPlayer().sendMessage(Main.getPrefix() + "Das System ist nicht aktiviert!");
                return;
            }

            this.openNext(new BotRegisterListGUI(this.getPlayer()));
        });
    }
}