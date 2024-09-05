package de.cuuky.varo.gui.admin;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.cuuky.cfw.utils.item.BuildItem;
import de.cuuky.cfw.version.types.Materials;
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
        addItem(4, new BuildItem().displayName("§eSetup Assistant").material(Materials.ENDER_EYE).build(),
                (event) -> this.openNext(new SetupHelpGUI(getPlayer())));

        addItem(10, new BuildItem().displayName("§cAlerts").material(Materials.BOOK).amount(getFixedSize(Alert.getOpenAlerts().size())).build(),
                (event) -> this.openNext(new AlertTypeChooseGUI(getPlayer())));

        addItem(16, new BuildItem().displayName("§cConfig").material(Materials.TRIPWIRE_HOOK).build(),
                (event) -> this.openNext(new ConfigSectionGUI(getPlayer())));

        addItem(18, new BuildItem().displayName("§4Reports").material(Materials.SIGN).amount(getFixedSize(Report.getReports().size())).build(),
                (event) -> this.openNext(new ReportListGUI(getPlayer())));

        addItem(22, new BuildItem().displayName("§aBackups").material(Materials.ENCHANTED_BOOK).build(),
                (event) -> this.openNext(new BackupListGUI(getPlayer())));

        addItem(26, new BuildItem().displayName("§1DiscordBot")
                        .itemstack(new ItemStack(Main.getBotLauncher().getDiscordbot() != null ? Material.ANVIL : Materials.GUNPOWDER.parseMaterial())).build(),
                (event) -> {
                    if (Main.getBotLauncher().getDiscordbot() == null) {
                        getPlayer().sendMessage(Main.getPrefix() + "Der DiscordBot wurde nicht aktiviert.");
                        getPlayer().sendMessage(Main.getPrefix() + "Bitte untersuche die Konsolenausgaben nach Fehlern und ueberpruefe, ob du den DiscordBot aktiviert hast.");
                        return;
                    }

                    this.openNext(new DiscordBotGUI(getPlayer()));
                });

        addItem(28, new BuildItem().displayName("§5Game").material(Materials.CAKE).build(),
                (event) -> this.openNext(new GameOptionsGUI(getPlayer())));

        addItem(34, new BuildItem().displayName("§6OreLogger").material(Materials.DIAMOND_ORE)
                        .amount(getFixedSize(Main.getDataManager().getVaroLoggerManager().getBlockLogger().getLogs().size())).build(),
                (event) -> this.openNext(new OreLoggerFilterGUI(getPlayer())));

        if (ConfigSetting.DEBUG_OPTIONS.getValueAsBoolean())
            addItem(this.getUsableSize(), new BuildItem().displayName("§6Debug").material(Materials.BUCKET).build(),
                    (event) -> this.openNext(new DebugGUI(getPlayer())));

        addItem(this.getSize() - 1, new BuildItem().displayName("§5Info")
                .material(Materials.MAP.parseMaterial()).build(), (event) -> this.sendInfo());
    }
}