package de.cuuky.varo.gui.report;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.gui.SuperInventory;
import de.cuuky.varo.gui.admin.AdminMainMenu;
import de.cuuky.varo.gui.utils.PageAction;
import de.cuuky.varo.item.ItemBuilder;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.report.Report;

public class ReportListGUI extends SuperInventory {

	public ReportListGUI(Player player) {
		super("§cReport List", player, 27, false);
		open();
	}

	@Override
	public boolean onOpen() {
		int start = getSize() * (getPage() - 1);
		for(int i = 0; i != getSize(); i++) {
			Report reports;
			try {
				reports = Report.getReports().get(start);
			} catch(IndexOutOfBoundsException e) {
				break;
			}

			ArrayList<String> lore = new ArrayList<>();
			lore.add("§c" + reports.getId());

			getInventory().setItem(i, new ItemBuilder().displayname("§7" + reports.getReported().getName()).itemstack(new ItemStack(Material.PAPER)).lore(lore).build());
			start++;
		}

		return calculatePages(Report.getReports().size(), getSize()) == page;
	}

	@Override
	public void onClose(InventoryCloseEvent event) {}

	@Override
	public void onClick(InventoryClickEvent event) {
		List<String> lore = event.getCurrentItem().getItemMeta().getLore();
		int id = Integer.parseInt(lore.get(0).replaceAll("§c", ""));
		Report report = Report.getReport(id);
		this.close(true);

		if(report == null) {
			update();
			return;
		}

		VaroPlayer vp = VaroPlayer.getPlayer(getOpener());

		new ReportPickGUI(vp, report);
	}

	private void update() {
		new ReportListGUI(getOpener());
	}

	@Override
	public void onInventoryAction(PageAction action) {}

	@Override
	public boolean onBackClick() {
		new AdminMainMenu(opener);
		return true;
	}
}
