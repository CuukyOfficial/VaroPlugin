package de.varoplugin.varo.gui.admin;

import org.bukkit.entity.Player;

import com.cryptomorin.xseries.XMaterial;

import de.varoplugin.cfw.item.ItemBuilder;
import de.varoplugin.varo.Main;
import de.varoplugin.varo.alert.Alert;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;
import de.varoplugin.varo.gui.VaroInventory;
import de.varoplugin.varo.gui.admin.alert.AlertTypeChooseGUI;
import de.varoplugin.varo.gui.admin.backup.BackupListGUI;
import de.varoplugin.varo.gui.admin.config.ConfigSectionGUI;
import de.varoplugin.varo.gui.admin.customcommands.CustomCommandMenuGUI;
import de.varoplugin.varo.gui.admin.discordbot.DiscordBotGUI;
import de.varoplugin.varo.gui.admin.orelogger.OreLoggerFilterGUI;
import de.varoplugin.varo.gui.report.ReportListGUI;
import de.varoplugin.varo.report.Report;

public class AdminMainMenu extends VaroInventory {

    public AdminMainMenu(Player player) {
        super(Main.getInventoryManager(), player);
    }

    @Override
    public String getTitle() {
        return Main.getProjectName() + " §8| §cAdmin";
    }

    @Override
    public int getSize() {
        return 45;
    }

    @Override
    public void refreshContent() {
        addItem(2, ItemBuilder.material(XMaterial.FIREWORK_ROCKET).displayName("§6Events").build(),
                (event) -> this.openNext(new VaroEventGUI(this.getPlayer())));
        
        addItem(4, ItemBuilder.material(XMaterial.ENDER_EYE).displayName("§eSetup Assistant").build(),
                (event) -> this.openNext(new SetupHelpGUI(getPlayer())));
        
        addItem(6, ItemBuilder.material(XMaterial.COMMAND_BLOCK).displayName("§cCustom Commands").build(),
                (event) -> this.openNext(new CustomCommandMenuGUI(this.getPlayer())));

        addItem(10, ItemBuilder.material(XMaterial.BOOK).displayName("§cAlerts").amount(getFixedSize(Alert.getOpenAlerts().size())).build(),
                (event) -> this.openNext(new AlertTypeChooseGUI(getPlayer())));

        addItem(16, ItemBuilder.material(XMaterial.STICKY_PISTON).displayName("§cConfig").build(),
                (event) -> this.openNext(new ConfigSectionGUI(getPlayer())));

        addItem(18, ItemBuilder.material(XMaterial.OAK_SIGN).displayName("§4Reports").amount(getFixedSize(Report.getReports().size())).build(),
                (event) -> this.openNext(new ReportListGUI(getPlayer())));

        addItem(22, ItemBuilder.material(XMaterial.ENCHANTED_BOOK).displayName("§aBackups").build(),
                (event) -> this.openNext(new BackupListGUI(getPlayer())));

        addItem(26, ItemBuilder.material(Main.getBotLauncher().getDiscordbot() != null ? XMaterial.ANVIL : XMaterial.GUNPOWDER)
                .displayName("§bDiscordBot").build(),
                (event) -> {
                    if (Main.getBotLauncher().getDiscordbot() == null) {
                        getPlayer().sendMessage(Main.getPrefix() + "Der DiscordBot wurde nicht aktiviert.");
                        getPlayer().sendMessage(Main.getPrefix() + "Bitte untersuche die Konsolenausgaben nach Fehlern und ueberpruefe, ob du den DiscordBot aktiviert hast.");
                        return;
                    }

                    this.openNext(new DiscordBotGUI(getPlayer()));
                });

        addItem(28, ItemBuilder.material(XMaterial.CAKE).displayName("§5Game").build(),
                (event) -> this.openNext(new GameOptionsGUI(getPlayer())));

        addItem(34, ItemBuilder.material(XMaterial.DIAMOND_ORE).displayName("§6OreLogger")
                        .amount(getFixedSize(Main.getDataManager().getVaroLoggerManager().getBlockLogger().getLogs().size())).build(),
                (event) -> this.openNext(new OreLoggerFilterGUI(getPlayer())));

        if (ConfigSetting.DEBUG_OPTIONS.getValueAsBoolean())
            addItem(this.getUsableSize(), ItemBuilder.material(XMaterial.BUCKET).displayName("§6Debug").build(),
                    (event) -> this.openNext(new DebugGUI(getPlayer())));
    }
}