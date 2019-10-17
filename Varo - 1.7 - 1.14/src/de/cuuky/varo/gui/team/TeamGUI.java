package de.cuuky.varo.gui.team;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.gui.SuperInventory;
import de.cuuky.varo.gui.utils.PageAction;
import de.cuuky.varo.item.ItemBuilder;
import de.cuuky.varo.team.Team;

public class TeamGUI extends SuperInventory {

	private Team team;

	public TeamGUI(Player opener, Team team) {
		super("§7Team §2" + team.getId(), opener, 9, false);

		this.team = team;
		open();
	}

	@Override
	public boolean onOpen() {
		linkItemTo(1, new ItemBuilder().displayname("§cRemove").itemstack(new ItemStack(Material.BUCKET)).build(),
				new Runnable() {

					@Override
					public void run() {
						team.delete();
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
		return false;
	}

	@Override
	public void onClose(InventoryCloseEvent event) {
	}
}
