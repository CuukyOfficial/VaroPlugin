package de.cuuky.varo.gui.report;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.cryptomorin.xseries.XMaterial;

import de.cuuky.varo.Main;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.gui.VaroListInventory;
import de.cuuky.varo.report.Report;
import de.varoplugin.cfw.inventory.ItemClick;
import de.varoplugin.cfw.item.ItemBuilder;

public class ReportListGUI extends VaroListInventory<Report> {

    public ReportListGUI(Player player) {
        super(Main.getInventoryManager(), player, Report.getReports());
    }

    @Override
    public String getTitle() {
        return "§cReport List";
    }

    @Override
    public int getSize() {
        return this.getRecommendedSize();
    }

    @Override
    protected ItemStack getItemStack(Report report) {
        List<String> lore = new ArrayList<>();
        lore.add("§7ID: §c" + report.getId());
        lore.add("§7Reason: §c" + report.getReason().getName());
        lore.add("§7Reported: §c" + (report.getReported() != null ? report.getReported().getName() : "-Deleted-"));
        lore.add("§7By: §c" + (report.getReporter() != null ? report.getReported().getName() : "-Deleted-"));
        return ItemBuilder.material(XMaterial.PAPER).displayName("§7" + report.getReported().getName()).lore(lore).build();
    }

    @Override
    protected ItemClick getClick(Report report) {
        return (event) -> this.openNext(new ReportPickGUI(VaroPlayer.getPlayer(getPlayer()), report));
    }
}