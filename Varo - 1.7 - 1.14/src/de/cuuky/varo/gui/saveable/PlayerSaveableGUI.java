package de.cuuky.varo.gui.saveable;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import de.cuuky.varo.entity.player.stats.stat.inventory.VaroSaveable;
import de.cuuky.varo.gui.SuperInventory;
import de.cuuky.varo.gui.utils.PageAction;
import de.cuuky.varo.item.ItemBuilder;
import de.cuuky.varo.version.types.Materials;

public class PlayerSaveableGUI extends SuperInventory {

	private VaroSaveable saveable;

	public PlayerSaveableGUI(Player opener, VaroSaveable saveable) {
		super("§7Saveable §e" + saveable.getId(), opener, 0, false);

		this.saveable = saveable;

		open();
	}

	@Override
	public boolean onOpen() {
		linkItemTo(1, new ItemBuilder().displayname("§cDelete").itemstack(Materials.REDSTONE.parseItem()).build(), new Runnable() {

			@Override
			public void run() {
				saveable.remove();
				back();
			}
		});
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
		new PlayerSaveableChooseGUI(opener, saveable.getPlayer());
		return true;
	}

	@Override
	public void onClose(InventoryCloseEvent event) {
	}
}
