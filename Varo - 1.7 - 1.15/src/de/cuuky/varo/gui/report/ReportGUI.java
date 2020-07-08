package de.cuuky.varo.gui.report;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.cfw.item.ItemBuilder;
import de.cuuky.cfw.menu.SuperInventory;
import de.cuuky.cfw.menu.utils.PageAction;
import de.cuuky.varo.Main;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.report.Report;
import de.cuuky.varo.report.ReportReason;

public class ReportGUI extends SuperInventory {

	private VaroPlayer reported;
	private VaroPlayer reporter;

	public ReportGUI(Player opener, VaroPlayer reported) {
		super("§cReport", opener, 18, false);

		this.reporter = VaroPlayer.getPlayer(opener);
		this.reported = reported;

		this.setModifier = true;
		Main.getCuukyFrameWork().getInventoryManager().registerInventory(this);
		open();
	}

	@Override
	public boolean onBackClick() {
		return false;
	}

	@Override
	public void onClick(InventoryClickEvent event) {
		this.close(true);

		String reportName = event.getCurrentItem().getItemMeta().getDisplayName().replace("§7", "");
		ReportReason reason = ReportReason.getByName(reportName);
		new Report(reporter, reported, reason);
		reporter.sendMessage(Main.getPrefix() + Main.getColorCode() + reported.getName() + " §7wurde erfolgreich reportet!");
	}

	@Override
	public void onClose(InventoryCloseEvent event) {}

	@Override
	public void onInventoryAction(PageAction action) {}

	@Override
	public boolean onOpen() {
		int i = -1;
		for (ReportReason reasons : ReportReason.values()) {
			i++;
			ArrayList<String> lore = new ArrayList<>();
			for (String strin : reasons.getDescription().split("\n"))
				lore.add("§c" + strin);

			getInventory().setItem(i, new ItemBuilder().displayname("§7" + reasons.getName()).itemstack(new ItemStack(reasons.getMaterial())).lore(lore).build());
		}

		return true;
	}
}