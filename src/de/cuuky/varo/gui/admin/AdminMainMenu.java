package de.cuuky.varo.gui.admin;

import org.bukkit.entity.Player;

import com.cryptomorin.xseries.XMaterial;

import de.cuuky.varo.Main;
import de.cuuky.varo.alert.Alert;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.gui.VaroInventory;
import de.cuuky.varo.gui.admin.alert.AlertTypeChooseGUI;
import de.cuuky.varo.gui.admin.backup.BackupListGUI;
import de.cuuky.varo.gui.admin.config.ConfigSectionGUI;
import de.cuuky.varo.gui.admin.debug.DebugGUI;
import de.cuuky.varo.gui.admin.discordbot.DiscordBotGUI;
import de.cuuky.varo.gui.admin.game.GameOptionsGUI;
import de.cuuky.varo.gui.admin.orelogger.OreLoggerFilterGUI;
import de.cuuky.varo.gui.admin.setuphelp.SetupHelpGUI;
import de.cuuky.varo.gui.report.ReportListGUI;
import de.cuuky.varo.report.Report;
import de.varoplugin.cfw.item.ItemBuilder;

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
        addItem(4, ItemBuilder.material(XMaterial.ENDER_EYE).displayName("§eSetup Assistant").build(),
                (event) -> this.openNext(new SetupHelpGUI(getPlayer())));

        addItem(10, ItemBuilder.material(XMaterial.BOOK).displayName("§cAlerts").amount(getFixedSize(Alert.getOpenAlerts().size())).build(),
                (event) -> this.openNext(new AlertTypeChooseGUI(getPlayer())));

        addItem(16, ItemBuilder.material(XMaterial.TRIPWIRE_HOOK).displayName("§cConfig").build(),
                (event) -> this.openNext(new ConfigSectionGUI(getPlayer())));

        addItem(18, ItemBuilder.material(XMaterial.OAK_SIGN).displayName("§4Reports").amount(getFixedSize(Report.getReports().size())).build(),
                (event) -> this.openNext(new ReportListGUI(getPlayer())));

        addItem(22, ItemBuilder.material(XMaterial.ENCHANTED_BOOK).displayName("§aBackups").build(),
                (event) -> this.openNext(new BackupListGUI(getPlayer())));

        addItem(26, ItemBuilder.material(Main.getBotLauncher().getDiscordbot() != null ? XMaterial.ANVIL : XMaterial.GUNPOWDER)
                .displayName("§1DiscordBot").build(),
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

        addItem(this.getSize() - 1, ItemBuilder.material(XMaterial.MAP).displayName("§5Info").build(),
                (event) -> this.sendInfo());
    }
}