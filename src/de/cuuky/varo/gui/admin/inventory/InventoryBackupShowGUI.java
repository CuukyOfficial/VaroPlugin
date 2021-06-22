package de.cuuky.varo.gui.admin.inventory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.cfw.item.ItemBuilder;
import de.cuuky.cfw.menu.utils.PageAction;
import de.cuuky.varo.Main;
import de.cuuky.varo.entity.player.stats.stat.inventory.InventoryBackup;
import de.cuuky.varo.gui.VaroSuperInventory;

public class InventoryBackupShowGUI extends VaroSuperInventory {

	private InventoryBackup backup;

	public InventoryBackupShowGUI(Player opener, InventoryBackup backup) {
		super("§7Inventory: §c" + backup.getVaroPlayer().getName(), opener, 54, false);

		this.backup = backup;
		this.setModifier = true;
		this.fillInventory = false;

		Main.getCuukyFrameWork().getInventoryManager().registerInventory(this);
		open();
	}

	@Override
	public boolean onBackClick() {
		new InventoryBackupGUI(opener, backup);
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
		for (int i = 0; i < backup.getInventory().getInventory().getContents().length; i++)
			inv.setItem(i, backup.getInventory().getInventory().getContents()[i]);

		for (int i = 0; i < backup.getArmor().size(); i++)
			inv.setItem(36 + i, backup.getArmor().get(i));

		linkItemTo(44, new ItemBuilder().itemstack(new ItemStack(Material.PAPER)).displayname("§aSave backup").build(), new Runnable() {

			@Override
			public void run() {
				backup.getInventory().getInventory().clear();
				backup.getArmor().clear();

				for (int i = 0; i < 36; i++) {
					if (inv.getItem(i) == null)
						continue;

					backup.getInventory().getInventory().setItem(i, inv.getItem(i));
				}

				for (int i = 0; i < 4; i++)
					backup.getArmor().add(inv.getItem(36 + i) == null ? new ItemStack(Material.AIR) : inv.getItem(36 + i));
			}
		});
		return true;
	}
}
