package de.cuuky.varo.gui.admin.backup;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.Main;
import de.cuuky.varo.backup.Backup;
import de.cuuky.varo.gui.SuperInventory;
import de.cuuky.varo.gui.utils.PageAction;
import de.cuuky.varo.item.ItemBuilder;
import de.cuuky.varo.version.types.Materials;

public class BackupGUI extends SuperInventory {

	private String filename;

	public BackupGUI(Player opener, String filename) {
		super("§7Backup §a" + filename.replace(".zip", ""), opener, 0, false);

		this.filename = filename;
		open();
	}

	@Override
	public boolean onOpen() {
		File file = new File("plugins/Varo/Backups/" + filename);

		int i = -1;
		do {
			i += 1;
			if (i != 1 && i != 4 && i != 7)
				inv.setItem(i, new ItemStack(Materials.BLACK_STAINED_GLASS_PANE.parseMaterial(), 1, (short) 15));
			else {
				if (i == 1)
					linkItemTo(i,
							new ItemBuilder().displayname("§aLoad").itemstack(new ItemStack(Material.EMERALD)).build(),
							new Runnable() {

								@Override
								public void run() {
									if (Backup.unzip(file.getPath(), "plugins/Varo")) {
										opener.sendMessage(Main.getPrefix() + "Backup erfolgreich wieder hergestellt!");
										Main.getDataManager().setDoSave(false);
										Bukkit.getServer().reload();
									} else
										opener.sendMessage(
												Main.getPrefix() + "Backup konnte nicht wieder hergestellt werden!");
								}
							});

				if (i == 7)
					linkItemTo(i,
							new ItemBuilder().displayname("§4Delete").itemstack(Materials.REDSTONE.parseItem()).build(),
							new Runnable() {

								@Override
								public void run() {
									file.delete();
									new BackupListGUI(opener);
								}
							});
			}
		} while (i != inv.getSize() - 1);

		return true;
	}

	@Override
	public void onClick(InventoryClickEvent event) {
	}

	@Override
	public void onInventoryAction(PageAction action) {
	}

	@Override
	public boolean onBackClick() {
		return false;
	}

	@Override
	public void onClose(InventoryCloseEvent event) {
	}
}
