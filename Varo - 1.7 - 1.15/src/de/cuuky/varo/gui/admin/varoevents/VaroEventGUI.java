package de.cuuky.varo.gui.admin.varoevents;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.Main;
import de.cuuky.varo.event.VaroEvent;
import de.cuuky.varo.game.state.GameState;
import de.cuuky.varo.gui.SuperInventory;
import de.cuuky.varo.gui.utils.PageAction;
import de.cuuky.varo.item.ItemBuilder;
import de.cuuky.varo.utils.JavaUtils;

public class VaroEventGUI extends SuperInventory {

	public VaroEventGUI(Player opener) {
		super("§5VaroEvents", opener, 9, false);

		open();
	}

	@Override
	public boolean onBackClick() {
		return false;
	}

	@Override
	public void onClick(InventoryClickEvent event) {
		updateInventory();
	}

	@Override
	public void onClose(InventoryCloseEvent event) {}

	@Override
	public void onInventoryAction(PageAction action) {

	}

	@Override
	public boolean onOpen() {
		int i = 0;
		for(VaroEvent event : VaroEvent.getEvents()) {
			linkItemTo(i, new ItemBuilder().displayname(event.getName()).itemstack(new ItemStack(event.getIcon())).lore(JavaUtils.combineArrays(new String[] { "§7Enabled: " + (event.isEnabled() ? "§a" : "§c") + event.isEnabled(), "" }, JavaUtils.addIntoEvery(event.getDescription().split("\n"), "§7", true))).build(), new Runnable() {

				@Override
				public void run() {
					if(Main.getVaroGame().getGameState() != GameState.STARTED) {
						opener.sendMessage(Main.getPrefix() + "Spiel wurde noch nicht gestartet!");
						return;
					}

					event.setEnabled(!event.isEnabled());
				}
			});

			i += 2;
		}

		return true;
	}
}
