package de.cuuky.varo.gui.player;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.gui.SuperInventory;
import de.cuuky.varo.gui.admin.AdminMainMenu;
import de.cuuky.varo.gui.player.PlayerListGUI.PlayerGUIType;
import de.cuuky.varo.gui.utils.PageAction;
import de.cuuky.varo.item.ItemBuilder;

public class PlayerListChooseGUI extends SuperInventory {

	private boolean showStats;

	public PlayerListChooseGUI(Player opener, boolean showStats) {
		super("§aChoose Players", opener, 9, false);

		this.showStats = showStats;
		open();
	}

	@Override
	public boolean onOpen() {
		int i = 0;
		for(PlayerGUIType type : PlayerGUIType.values()) {
			linkItemTo(i, new ItemBuilder().displayname(type.getTypeName()).itemstack(new ItemStack(type.getIcon())).amount(getFixedSize(type.getList().size())).build(), new Runnable() {

				@Override
				public void run() {
					new PlayerListGUI(opener, showStats, type);
				}
			});
			i += 2;
		}

		return true;
	}

	@Override
	public void onClose(InventoryCloseEvent event) {}

	@Override
	public void onClick(InventoryClickEvent event) {}

	@Override
	public void onInventoryAction(PageAction action) {}

	@Override
	public boolean onBackClick() {
		if(opener.hasPermission("varo.admin")) {
			new AdminMainMenu(opener);
			return true;
		}
		return false;
	}

}
