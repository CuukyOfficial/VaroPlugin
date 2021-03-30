package de.cuuky.varo.gui.admin.backup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.cfw.item.ItemBuilder;
import de.cuuky.varo.gui.VaroSuperInventory;
import de.cuuky.cfw.menu.utils.PageAction;
import de.cuuky.varo.Main;
import de.cuuky.varo.gui.admin.AdminMainMenu;
import de.cuuky.varo.recovery.recoveries.VaroBackup;

public class BackupListGUI extends VaroSuperInventory {

	public BackupListGUI(Player opener) {
		super("§aBackups", opener, 54, false);

		this.setModifier = true;
		Main.getCuukyFrameWork().getInventoryManager().registerInventory(this);
		open();
	}

	@Override
	public boolean onBackClick() {
		new AdminMainMenu(opener);
		return true;
	}

	@Override
	public void onClick(InventoryClickEvent event) {}

	@Override
	public void onClose(InventoryCloseEvent event) {}

	@Override
	public void onInventoryAction(PageAction action) {}

	@Override
	public boolean onOpen() {
		ArrayList<VaroBackup> backups = VaroBackup.getBackups();
		int start = getSize() * (getPage() - 1);
		if (start != 0)
			start -= 2;

		for (int i = 0; i != getSize() - 2; i++) {
			VaroBackup backup;
			try {
				backup = backups.get(start);
			} catch (IndexOutOfBoundsException e) {
				break;
			}

			ArrayList<String> lore = new ArrayList<>();
			lore.add("Backup made date: ");
			String[] split1 = backup.getZipFile().getName().split("_");
			lore.add("Year: " + split1[0].split("-")[0] + ", Month: " + split1[0].split("-")[1] + ", Day: " + split1[0].split("-")[2]);
			lore.add("Hour: " + split1[1].split("-")[0] + ", Minute: " + split1[1].split("-")[1] + ", Second: " + split1[1].split("-")[2].replace(".zip", ""));
			linkItemTo(i, new ItemBuilder().displayname("§7" + backup.getZipFile().getName().replace(".zip", "")).itemstack(new ItemStack(Material.DISPENSER)).lore(lore).build(), new Runnable() {

				@Override
				public void run() {
					new BackupGUI(opener, backup);
				}
			});
			start++;
		}

		linkItemTo(44, new ItemBuilder().displayname("§aCreate Backup").itemstack(new ItemStack(Material.EMERALD)).build(), new Runnable() {

			private String getCurrentDate() {
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
				Date date = new Date();

				return dateFormat.format(date);
			}

			@Override
			public void run() {
				if (VaroBackup.getBackup(getCurrentDate()) != null) {
					opener.sendMessage(Main.getPrefix() + "Warte kurz, bevor du ein neues Backup erstellen kannst.");
					return;
				}

				new VaroBackup();
				updateInventory();
			}
		});

		return calculatePages(backups.size(), getSize() - 2) == page;
	}
}