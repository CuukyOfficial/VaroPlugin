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

import de.cuuky.varo.Main;
import de.cuuky.varo.backup.Backup;
import de.cuuky.varo.gui.SuperInventory;
import de.cuuky.varo.gui.admin.AdminMainMenu;
import de.cuuky.varo.gui.utils.PageAction;
import de.cuuky.varo.item.ItemBuilder;

public class BackupListGUI extends SuperInventory {

	public BackupListGUI(Player opener) {
		super("§aBackups", opener, 45, false);

		open();
	}

	@Override
	public boolean onOpen() {
		ArrayList<String> list = Backup.getBackups();
		int start = getSize() * (getPage() - 1);
		if(start != 0)
			start -= 2;

		for(int i = 0; i != getSize() - 2; i++) {
			String filename;
			try {
				filename = list.get(start);
			} catch(IndexOutOfBoundsException e) {
				break;
			}

			ArrayList<String> lore = new ArrayList<>();
			lore.add("Backup made date: ");
			String[] split1 = filename.split("_");
			lore.add("Year: " + split1[0].split("-")[0] + ", Month: " + split1[0].split("-")[1] + ", Day: " + split1[0].split("-")[2]);
			lore.add("Hour: " + split1[1].split("-")[0] + ", Minute: " + split1[1].split("-")[1] + ", Second: " + split1[1].split("-")[2].replace(".zip", ""));
			linkItemTo(i, new ItemBuilder().displayname("§7" + filename).itemstack(new ItemStack(Material.DISPENSER)).lore(lore).build(), new Runnable() {

				@Override
				public void run() {
					new BackupGUI(opener, filename);
				}
			});
			start++;
		}

		linkItemTo(44, new ItemBuilder().displayname("§aCreate Backup").itemstack(new ItemStack(Material.EMERALD)).build(), new Runnable() {

			@Override
			public void run() {
				if(Backup.isBackup(getCurrentDate())) {
					opener.sendMessage(Main.getPrefix() + "Warte kurz, bevor du ein neues Backup erstellen kannst.");
					return;
				}

				new Backup();
				updateInventory();
			}

			private String getCurrentDate() {
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
				Date date = new Date();

				return dateFormat.format(date);
			}
		});

		return calculatePages(list.size(), getSize() - 2) == page;
	}

	@Override
	public void onClick(InventoryClickEvent event) {}

	@Override
	public void onInventoryAction(PageAction action) {}

	@Override
	public boolean onBackClick() {
		new AdminMainMenu(opener);
		return true;
	}

	@Override
	public void onClose(InventoryCloseEvent event) {}
}
