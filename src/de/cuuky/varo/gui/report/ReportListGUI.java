package de.cuuky.varo.gui.report;

import de.cuuky.cfw.inventory.ItemClick;
import de.cuuky.cfw.utils.item.BuildItem;
import de.cuuky.varo.app.Main;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.gui.VaroListInventory;
import de.cuuky.varo.Report;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ReportListGUI extends VaroListInventory<Report> {

    public ReportListGUI(Player player) {
        super(Main.getCuukyFrameWork().getAdvancedInventoryManager(), player, Report.getReports());
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
        return new BuildItem().displayName("§7" + report.getReported().getName()).itemstack(new ItemStack(Material.PAPER)).lore(lore).build();
    }

    @Override
    protected ItemClick getClick(Report report) {
        return (event) -> this.openNext(new ReportPickGUI(VaroPlayer.getPlayer(getPlayer()), report));
    }
}