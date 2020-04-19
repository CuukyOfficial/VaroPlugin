package de.cuuky.varo.gui.saveable;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import de.cuuky.cfw.item.ItemBuilder;
import de.cuuky.cfw.menu.SuperInventory;
import de.cuuky.cfw.menu.utils.PageAction;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.Main;
import de.cuuky.varo.entity.player.stats.stat.inventory.VaroSaveable;

public class PlayerSaveableGUI extends SuperInventory {

	private VaroSaveable saveable;

	public PlayerSaveableGUI(Player opener, VaroSaveable saveable) {
		super("§7Saveable §e" + saveable.getId(), opener, 0, false);

		this.saveable = saveable;

		this.setModifier = true;
		Main.getCuukyFrameWork().getInventoryManager().registerInventory(this);
		open();
	}

	@Override
	public boolean onBackClick() {
		new PlayerSaveableChooseGUI(opener, saveable.getPlayer());
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
		linkItemTo(1, new ItemBuilder().displayname("§cDelete").itemstack(Materials.REDSTONE.parseItem()).build(), new Runnable() {

			@Override
			public void run() {
				saveable.remove();
				back();
			}
		});
		return true;
	}
}
