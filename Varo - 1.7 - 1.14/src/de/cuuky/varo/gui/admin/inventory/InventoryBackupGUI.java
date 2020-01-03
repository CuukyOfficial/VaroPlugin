package de.cuuky.varo.gui.admin.inventory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.Main;
import de.cuuky.varo.entity.player.stats.stat.inventory.InventoryBackup;
import de.cuuky.varo.gui.SuperInventory;
import de.cuuky.varo.gui.utils.PageAction;
import de.cuuky.varo.item.ItemBuilder;
import de.cuuky.varo.version.types.Materials;

public class InventoryBackupGUI extends SuperInventory {

	private InventoryBackup backup;

	public InventoryBackupGUI(Player opener, InventoryBackup backup) {
		super("§b" + backup.getVaroPlayer().getName(), opener, 9, false);

		this.backup = backup;

		open();
	}

	@Override
	public boolean onBackClick() {
		new InventoryBackupListGUI(opener, backup.getVaroPlayer());
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
		linkItemTo(1, new ItemBuilder().displayname("§aShow").itemstack(new ItemStack(Material.CHEST)).build(), new Runnable() {

			@Override
			public void run() {
				new InventoryBackupShowGUI(opener, backup);
			}
		});

		linkItemTo(4, new ItemBuilder().displayname("§2Restore").itemstack(new ItemStack(Material.EMERALD)).build(), new Runnable() {

			@Override
			public void run() {
				if(!backup.getVaroPlayer().isOnline()) {
					backup.getVaroPlayer().getStats().setRestoreBackup(backup);
					opener.sendMessage(Main.getPrefix() + "Inventar wird beim nächsten Betreten wiederhergestellt!");
					return;
				}

				backup.restoreUpdate(backup.getVaroPlayer().getPlayer());
				opener.sendMessage(Main.getPrefix() + "Inventar wurde wiederhergestellt!");
			}
		});

		linkItemTo(7, new ItemBuilder().displayname("§cRemove").itemstack(Materials.REDSTONE.parseItem()).build(), new Runnable() {

			@Override
			public void run() {
				backup.getVaroPlayer().getStats().removeInventoryBackup(backup);
				new InventoryBackupListGUI(opener, backup.getVaroPlayer());
			}
		});

		return true;
	}
}
