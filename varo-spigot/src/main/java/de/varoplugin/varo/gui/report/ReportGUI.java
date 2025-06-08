package de.varoplugin.varo.gui.report;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.varoplugin.cfw.inventory.ItemClick;
import de.varoplugin.cfw.item.ItemBuilder;
import de.varoplugin.varo.Main;
import de.varoplugin.varo.gui.VaroListInventory;
import de.varoplugin.varo.player.VaroPlayer;
import de.varoplugin.varo.report.Report;
import de.varoplugin.varo.report.ReportReason;

public class ReportGUI extends VaroListInventory<ReportReason> {

    private final VaroPlayer reported, reporter;

    public ReportGUI(Player player, VaroPlayer reported) {
        super(Main.getInventoryManager(), player, Arrays.asList(ReportReason.values()));

        this.reporter = VaroPlayer.getPlayer(player);
        this.reported = reported;
    }

    @Override
    public String getTitle() {
        return "§cReport";
    }

    @Override
    public int getSize() {
        return 18;
    }

    @Override
    protected ItemStack getItemStack(ReportReason reason) {
        List<String> lore = Arrays.stream(reason.getDescription().split("\n")).map(s -> "§7" + s).collect(Collectors.toList());
        return ItemBuilder.material(reason.getMaterial()).displayName("§c" + reason.getName()).lore(lore).build();
    }

    @Override
    protected ItemClick getClick(ReportReason reason) {
        return (event) -> {
            this.close();
            new Report(reporter, reported, reason);
            reporter.sendMessage(Main.getPrefix() + Main.getColorCode() + reported.getName() + " §7wurde erfolgreich reportet!");
        };
    }
}