package de.cuuky.varo.gui.report;

import com.cryptomorin.xseries.XMaterial;

import de.cuuky.varo.Main;
import de.cuuky.varo.gui.VaroInventory;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.report.Report;
import de.varoplugin.cfw.item.ItemBuilder;

public class ReportPickGUI extends VaroInventory {

    private final Report report;
    private final VaroPlayer player;

    public ReportPickGUI(VaroPlayer player, Report report) {
        super(Main.getInventoryManager(), player.getPlayer());

        this.report = report;
        this.player = player;
    }

    @Override
    public String getTitle() {
        return "§cReport " + report.getId();
    }

    @Override
    public int getSize() {
        return 36;
    }

    @Override
    public void refreshContent() {
        addItem(11, ItemBuilder.material(XMaterial.ENDER_PEARL).displayName("§5Teleport").build(), (event) -> {
            if (report.getReported().isOnline()) {
                player.saveTeleport(report.getReported().getPlayer().getLocation());
                player.sendMessage(Main.getPrefix() + "§7Du wurdest zum reporteten Spieler teleportiert!");
            } else
                player.sendMessage(Main.getPrefix() + "§7Der reportete Spieler ist nicht mehr online!");
        });

        addItem(15, ItemBuilder.material(XMaterial.RED_DYE).displayName("§cClose").build(), (event) -> {
            player.sendMessage(Main.getPrefix() + "§7Du hast den Report §c" + report.getId() + " §7geschlossen");
            report.close();
            back();
        });
    }
}