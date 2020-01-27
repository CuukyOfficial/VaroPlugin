package de.cuuky.varo.gui.admin.inventory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.Main;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.stats.stat.inventory.InventoryBackup;
import de.cuuky.varo.gui.SuperInventory;
import de.cuuky.varo.gui.player.PlayerGUI;
import de.cuuky.varo.gui.utils.PageAction;
import de.cuuky.varo.item.ItemBuilder;

public class InventoryBackupListGUI extends SuperInventory {

	private VaroPlayer target;

	public InventoryBackupListGUI(Player opener, VaroPlayer target) {
		super("§7Backups: §b" + target.getName(), opener, 45, false);

		this.target = target;

		open();
	}

	@Override
	public boolean onBackClick() {
		new PlayerGUI(opener, target, null);
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
		ArrayList<InventoryBackup> backups = target.getStats().getInventoryBackups();

		int start = getSize() * (getPage() - 1);
		if(start != 0)
			start -= 2;

		for(int i = 0; i != getSize() - 2; i++) {
			InventoryBackup backup;
			try {
				backup = backups.get(start);
			} catch(IndexOutOfBoundsException e) {
				break;
			}

			linkItemTo(i, new ItemBuilder().displayname(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(backup.getDate())).itemstack(new ItemStack(Material.BOOK)).build(), new Runnable() {

				@Override
				public void run() {
					new InventoryBackupGUI(opener, backup);
				}
			});
			start++;
		}

		linkItemTo(getSize() - 1, new ItemBuilder().displayname("§aCreate Backup").itemstack(new ItemStack(Material.EMERALD)).build(), new Runnable() {

			@Override
			public void run() {
				if(!target.isOnline()) {
					opener.sendMessage(Main.getPrefix() + "Dieser Spieler ist nicht online!");
					return;
				}

				target.getStats().addInventoryBackup(new InventoryBackup(target));
				updateInventory();
			}
		});

		return calculatePages(backups.size(), getSize() - 2) == getPage();
	}
}
