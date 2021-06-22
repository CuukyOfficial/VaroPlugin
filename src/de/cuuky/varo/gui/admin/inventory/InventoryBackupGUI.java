package de.cuuky.varo.gui.admin.inventory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.cfw.item.ItemBuilder;
import de.cuuky.cfw.menu.utils.PageAction;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.Main;
import de.cuuky.varo.entity.player.stats.stat.inventory.InventoryBackup;
import de.cuuky.varo.gui.VaroSuperInventory;

public class InventoryBackupGUI extends VaroSuperInventory {

	private InventoryBackup backup;

	public InventoryBackupGUI(Player opener, InventoryBackup backup) {
		super("§b" + backup.getVaroPlayer().getName(), opener, 18, false);

		this.backup = backup;

		this.setModifier = true;
		Main.getCuukyFrameWork().getInventoryManager().registerInventory(this);
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
				if (!backup.getVaroPlayer().isOnline()) {
					backup.getVaroPlayer().getStats().setRestoreBackup(backup);
					opener.sendMessage(Main.getPrefix() + "Inventar wird beim naechsten Betreten wiederhergestellt!");
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
