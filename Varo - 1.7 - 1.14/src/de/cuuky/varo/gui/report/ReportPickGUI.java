package de.cuuky.varo.gui.report;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.Main;
import de.cuuky.varo.gui.SuperInventory;
import de.cuuky.varo.gui.utils.PageAction;
import de.cuuky.varo.item.ItemBuilder;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.report.Report;
import de.cuuky.varo.version.types.Materials;

public class ReportPickGUI extends SuperInventory {

	private Report report;
	private VaroPlayer varoPlayer;

	public ReportPickGUI(VaroPlayer opener, Report report) {
		super("§cReport " + report.getId(), opener.getPlayer(), 9, false);

		this.report = report;
		this.varoPlayer = opener;

		open();
	}

	@Override
	public boolean onOpen() {
		linkItemTo(0, new ItemBuilder().displayname("§5Teleport").itemstack(new ItemStack(Material.ENDER_PEARL)).build(), new Runnable() {

			@Override
			public void run() {
				if(report.getReported().isOnline()) {
					varoPlayer.getPlayer().teleport(report.getReported().getPlayer());
					varoPlayer.sendMessage(Main.getPrefix() + "§7Du wurdest zum reporteten Spieler teleportiert!");
					return;
				}

				varoPlayer.sendMessage(Main.getPrefix() + "§7Der reportete Spieler ist nicht mehr online!");
			}
		});

		linkItemTo(8, new ItemBuilder().displayname("§cClose").itemstack(Materials.REDSTONE.parseItem()).build(), new Runnable() {

			@Override
			public void run() {
				varoPlayer.sendMessage(Main.getPrefix() + "§7Du hast den Report §c" + +report.getId() + " §7geschlossen");
				report.close();
				new ReportListGUI(varoPlayer.getPlayer());
			}
		});

		return true;
	}

	@Override
	public void onClose(InventoryCloseEvent event) {}

	@Override
	public void onClick(InventoryClickEvent event) {}

	@Override
	public void onInventoryAction(PageAction action) {}

	@Override
	public boolean onBackClick() {
		new ReportListGUI(varoPlayer.getPlayer());
		return true;
	}

}
