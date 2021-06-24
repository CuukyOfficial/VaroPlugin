package de.cuuky.varo.gui.report;

import de.cuuky.cfw.item.ItemBuilder;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.Main;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.gui.VaroInventory;
import de.cuuky.varo.report.Report;

public class ReportPickGUI extends VaroInventory {

    private final Report report;
    private final VaroPlayer player;

    public ReportPickGUI(VaroPlayer player, Report report) {
        super(Main.getCuukyFrameWork().getAdvancedInventoryManager(), player.getPlayer());

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
        addItem(11, new ItemBuilder().displayname("§5Teleport").itemstack(Materials.ENDER_PEARL.parseItem()).build(), (event) -> {
            if (report.getReported().isOnline()) {
                player.saveTeleport(report.getReported().getPlayer().getLocation());
                player.sendMessage(Main.getPrefix() + "§7Du wurdest zum reporteten Spieler teleportiert!");
            } else
                player.sendMessage(Main.getPrefix() + "§7Der reportete Spieler ist nicht mehr online!");
        });

        addItem(15, new ItemBuilder().displayname("§cClose").itemstack(Materials.ROSE_RED.parseItem()).build(), (event) -> {
            player.sendMessage(Main.getPrefix() + "§7Du hast den Report §c" + +report.getId() + " §7geschlossen");
            report.close();
            back();
        });
    }
}