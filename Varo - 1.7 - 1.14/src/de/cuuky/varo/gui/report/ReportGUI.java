package de.cuuky.varo.gui.report;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.Main;
import de.cuuky.varo.gui.SuperInventory;
import de.cuuky.varo.gui.utils.PageAction;
import de.cuuky.varo.item.ItemBuilder;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.report.Report;
import de.cuuky.varo.report.ReportReason;

public class ReportGUI extends SuperInventory {

	private VaroPlayer reported;
	private VaroPlayer reporter;

	public ReportGUI(Player opener, VaroPlayer reported) {
		super("§cReport", opener, 9, false);

		this.reporter = VaroPlayer.getPlayer(opener);
		this.reported = reported;

		open();
	}

	@Override
	public boolean onOpen() {
		int i = -1;
		for(ReportReason reasons : ReportReason.values()) {
			i++;
			ArrayList<String> lore = new ArrayList<>();
			for(String strin : reasons.getDescription().split("\n"))
				lore.add("§c" + strin);

			getInventory().setItem(i, new ItemBuilder().displayname("§7" + reasons.getName()).itemstack(new ItemStack(reasons.getMaterial())).lore(lore).build());
		}

		return true;
	}

	@Override
	public void onClose(InventoryCloseEvent event) {}

	@Override
	public void onClick(InventoryClickEvent event) {
		this.close(true);

		String reportName = event.getCurrentItem().getItemMeta().getDisplayName().replaceAll("§7", "");
		ReportReason reason = ReportReason.getByName(reportName);
		new Report(reporter, reported, reason);
		reporter.sendMessage(Main.getPrefix() + Main.getColorCode() + reported.getName() + " §7wurde erfolgreich reportet!");
	}

	@Override
	public void onInventoryAction(PageAction action) {}

	@Override
	public boolean onBackClick() {
		return false;
	}

}
