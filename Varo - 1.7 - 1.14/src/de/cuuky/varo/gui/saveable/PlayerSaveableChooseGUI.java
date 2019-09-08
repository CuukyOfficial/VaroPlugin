package de.cuuky.varo.gui.saveable;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.Main;
import de.cuuky.varo.gui.SuperInventory;
import de.cuuky.varo.gui.utils.PageAction;
import de.cuuky.varo.item.ItemBuilder;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.player.stats.stat.inventory.VaroSaveable;
import de.cuuky.varo.player.stats.stat.inventory.VaroSaveable.SaveableType;
import de.cuuky.varo.utils.LocationFormatter;

public class PlayerSaveableChooseGUI extends SuperInventory {

	private VaroPlayer target;

	public PlayerSaveableChooseGUI(Player opener, VaroPlayer target) {
		super("ße÷fen/Kisten", opener, 45, false);

		this.target = target;

		open();
	}

	@Override
	public boolean onOpen() {
		ArrayList<VaroSaveable> list = VaroSaveable.getSaveable(target);

		int start = getSize() * (getPage() - 1);
		for(int i = 0; i != getSize(); i++) {
			VaroSaveable saveable;
			try {
				saveable = list.get(start);
			} catch(IndexOutOfBoundsException e) {
				break;
			}

			linkItemTo(i, new ItemBuilder().displayname(Main.getColorCode() + String.valueOf(saveable.getId())).itemstack(new ItemStack(saveable.getType() == SaveableType.CHEST ? Material.CHEST : Material.FURNACE)).lore("ß7Locationß8: " + new LocationFormatter(Main.getColorCode() + "xß7, " + Main.getColorCode() + "yß7, " + Main.getColorCode() + "zß7 in " + Main.getColorCode() + "world").format(saveable.getBlock().getLocation()).split("-+")).build(), new Runnable() {

				@Override
				public void run() {
					new PlayerSaveableGUI(opener, saveable);
				}
			});
			start++;
		}

		return calculatePages(list.size(), getSize()) == page;
	}

	@Override
	public void onClick(InventoryClickEvent event) {}

	@Override
	public void onInventoryAction(PageAction action) {}

	@Override
	public boolean onBackClick() {
		return false;
	}

	@Override
	public void onClose(InventoryCloseEvent event) {}
}
