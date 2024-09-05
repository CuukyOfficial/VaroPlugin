package de.cuuky.varo.gui.report;

import de.varoplugin.cfw.inventory.ItemClick;
import de.cuuky.cfw.utils.item.BuildItem;
import de.cuuky.varo.Main;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.gui.VaroListInventory;
import de.cuuky.varo.report.Report;
import de.cuuky.varo.report.ReportReason;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        return new BuildItem().displayName("§c" + reason.getName()).itemstack(new ItemStack(reason.getMaterial())).lore(lore).build();
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