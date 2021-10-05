package de.cuuky.varo.gui.admin;

import de.cuuky.cfw.utils.item.BuildItem;
import de.cuuky.cfw.utils.item.BuildSkull;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.Main;
import de.cuuky.varo.alert.Alert;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.team.VaroTeam;
import de.cuuky.varo.gui.VaroInventory;
import de.cuuky.varo.gui.admin.alert.AlertTypeChooseGUI;
import de.cuuky.varo.gui.admin.backup.BackupListGUI;
import de.cuuky.varo.gui.admin.config.ConfigSectionGUI;
import de.cuuky.varo.gui.admin.debug.DebugGUI;
import de.cuuky.varo.gui.admin.discordbot.DiscordBotGUI;
import de.cuuky.varo.gui.admin.game.GameOptionsGUI;
import de.cuuky.varo.gui.admin.orelogger.OreLoggerListGUI;
import de.cuuky.varo.gui.admin.setuphelp.SetupHelpGUI;
import de.cuuky.varo.gui.player.PlayerListChooseGUI;
import de.cuuky.varo.gui.report.ReportListGUI;
import de.cuuky.varo.gui.team.TeamChooseGUI;
import de.cuuky.varo.report.Report;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AdminMainMenu extends VaroInventory {

    public AdminMainMenu(Player player) {
        super(Main.getCuukyFrameWork().getAdvancedInventoryManager(), player);
    }

    @Override
    public String getTitle() {
        return Main.getProjectName() + " §8| §cAdmin";
    }

    @Override
    public int getSize() {
        return 54;
    }

    @Override
    public void refreshContent() {
        addItem(0, new BuildItem().displayName("§eSetup Assistant").material(Materials.ENDER_EYE).build(),
                (event) -> this.openNext(new SetupHelpGUI(getPlayer())));

        addItem(4, new BuildItem().displayName("§cConfig").material(Materials.WHEAT).build(),
                (event) -> this.openNext(new ConfigSectionGUI(getPlayer())));

        addItem(10, new BuildItem().displayName("§4Reports").itemstack(new ItemStack(Material.BLAZE_ROD)).amount(getFixedSize(Report.getReports().size())).build(),
                (event) -> this.openNext(new ReportListGUI(getPlayer())));

        addItem(16, new BuildSkull().player(getPlayer().getName()).displayName("§aSpieler").amount(getFixedSize(VaroPlayer.getVaroPlayer().size())).build(),
                (event) -> this.openNext(new PlayerListChooseGUI(getPlayer())));

        addItem(18, new BuildItem().displayName("§cAlerts").itemstack(new ItemStack(Material.BOOK)).amount(getFixedSize(Alert.getOpenAlerts().size())).build(),
                (event) -> this.openNext(new AlertTypeChooseGUI(getPlayer())));

        addItem(22, new BuildItem().displayName("§aBackups").material(Materials.WRITTEN_BOOK).build(),
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

        addItem(34, new BuildItem().displayName("§2Teams").itemstack(new ItemStack(Material.DIAMOND_HELMET))
                        .amount(getFixedSize(VaroTeam.getTeams().size())).build(),
                (event) -> this.openNext(new TeamChooseGUI(getPlayer())));

        addItem(40, new BuildItem().displayName("§6OreLogger").itemstack(new ItemStack(Material.DIAMOND_ORE))
                        .amount(getFixedSize(Main.getDataManager().getVaroLoggerManager().getBlockLogger().getLogs().size())).build(),
                (event) -> this.openNext(new OreLoggerListGUI(getPlayer())));

        if (ConfigSetting.DEBUG_OPTIONS.getValueAsBoolean())
            addItem(this.getUsableSize(), new BuildItem().displayName("§6Debug").itemstack(new ItemStack(Material.BUCKET)).build(),
                    (event) -> this.openNext(new DebugGUI(getPlayer())));

        addItem(this.getSize() - 1, new BuildItem().displayName("§5Info")
                .material(Materials.MAP.parseMaterial()).build(), (event) -> this.sendInfo());
    }
}