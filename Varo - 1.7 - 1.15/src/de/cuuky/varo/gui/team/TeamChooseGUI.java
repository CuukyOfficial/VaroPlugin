package de.cuuky.varo.gui.team;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.cfw.item.ItemBuilder;
import de.cuuky.cfw.menu.utils.PageAction;
import de.cuuky.varo.Main;
import de.cuuky.varo.gui.MainMenu;
import de.cuuky.varo.gui.VaroSuperInventory;
import de.cuuky.varo.gui.admin.AdminMainMenu;
import de.cuuky.varo.gui.team.TeamListGUI.TeamGUIType;

public class TeamChooseGUI extends VaroSuperInventory {

	public TeamChooseGUI(Player opener) {
		super("§3Choose Category", opener, 18, false);

		this.setModifier = true;
		Main.getCuukyFrameWork().getInventoryManager().registerInventory(this);
		open();
	}

	@Override
	public boolean onBackClick() {
		if (opener.hasPermission("varo.admin"))
			new AdminMainMenu(opener);
		else
			new MainMenu(opener);

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
		int i = 1;
		for (TeamGUIType type : TeamGUIType.values()) {
			linkItemTo(i, new ItemBuilder().displayname(type.getTypeName()).itemstack(new ItemStack(type.getIcon())).amount(getFixedSize(type.getList().size())).build(), new Runnable() {

				@Override
				public void run() {
					new TeamListGUI(opener, type);
				}
			});
			i += 2;
		}

		return true;
	}
}
