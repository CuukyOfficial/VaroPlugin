package de.cuuky.varo.gui.team;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.gui.SuperInventory;
import de.cuuky.varo.gui.admin.AdminMainMenu;
import de.cuuky.varo.gui.team.TeamListGUI.TeamGUIType;
import de.cuuky.varo.gui.utils.PageAction;
import de.cuuky.varo.item.ItemBuilder;

public class TeamChooseGUI extends SuperInventory {

	public TeamChooseGUI(Player opener) {
		super("§3Choose Category", opener, 9, false);

		open();
	}

	@Override
	public boolean onOpen() {
		int i = 1;
		for (TeamGUIType type : TeamGUIType.values()) {
			linkItemTo(i, new ItemBuilder().displayname(type.getTypeName()).itemstack(new ItemStack(type.getIcon()))
					.amount(getFixedSize(type.getList().size())).build(), new Runnable() {

						@Override
						public void run() {
							new TeamListGUI(opener, type);
						}
					});
			i += 2;
		}

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
		if (opener.hasPermission("varo.admin")) {
			new AdminMainMenu(opener);
			return true;
		}

		return false;
	}

	@Override
	public void onClose(InventoryCloseEvent event) {
	}
}
