package de.cuuky.varo.gui.admin.backup;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.cfw.item.ItemBuilder;
import de.cuuky.varo.gui.VaroSuperInventory;
import de.cuuky.cfw.menu.utils.PageAction;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.Main;
import de.cuuky.varo.recovery.recoveries.VaroBackup;

public class BackupGUI extends VaroSuperInventory {

	private VaroBackup backup;

	public BackupGUI(Player opener, VaroBackup backup) {
		super("§7Backup §a" + backup.getZipFile().getName().replace(".zip", ""), opener, 9, false);

		this.backup = backup;

		this.setModifier = true;
		Main.getCuukyFrameWork().getInventoryManager().registerInventory(this);
		open();
	}

	@Override
	public boolean onBackClick() {
		new BackupListGUI(opener);
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
		int i = -1;
		do {
			i += 1;
			if (i != 1 && i != 4 && i != 7)
				inv.setItem(i, new ItemStack(Materials.BLACK_STAINED_GLASS_PANE.parseMaterial(), 1, (short) 15));
			else {
				if (i == 1)
					linkItemTo(i, new ItemBuilder().displayname("§aLoad").itemstack(new ItemStack(Material.EMERALD)).build(), new Runnable() {

						@Override
						public void run() {
							if (backup.unzip("plugins/Varo")) {
								opener.sendMessage(Main.getPrefix() + "Backup erfolgreich wieder hergestellt!");
								closeInventory();
								Main.getDataManager().setDoSave(false);
								Bukkit.getServer().reload();
							} else
								opener.sendMessage(Main.getPrefix() + "Backup konnte nicht wieder hergestellt werden!");
						}
					});

				if (i == 7)
					linkItemTo(i, new ItemBuilder().displayname("§4Delete").itemstack(Materials.REDSTONE.parseItem()).build(), new Runnable() {

						@Override
						public void run() {
							backup.delete();
							new BackupListGUI(opener);
						}
					});
			}
		} while (i != inv.getSize() - 1);

		return true;
	}
}