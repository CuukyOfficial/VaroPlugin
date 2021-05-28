package de.cuuky.varo.gui.admin.orelogger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.cfw.item.ItemBuilder;
import de.cuuky.cfw.menu.utils.PageAction;
import de.cuuky.cfw.utils.BukkitUtils;
import de.cuuky.varo.Main;
import de.cuuky.varo.gui.VaroSuperInventory;
import de.cuuky.varo.gui.admin.AdminMainMenu;
import de.cuuky.varo.logger.logger.LoggedBlock;

public class OreLoggerListGUI extends VaroSuperInventory {

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	public OreLoggerListGUI(Player opener) {
		super("§6OreLogger", opener, 54, false);

		this.setModifier = true;
		Main.getCuukyFrameWork().getInventoryManager().registerInventory(this);
		open();
	}

	@Override
	public boolean onBackClick() {
		new AdminMainMenu(opener);
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
		List<LoggedBlock> list = Main.getDataManager().getVaroLoggerManager().getBlockLogger().getLogs();

		int index = getSize() * (getPage() - 1);
		for (int i = 0; i < getSize(); i++) {
			if(index < 0 || index >= list.size())
				break;

			LoggedBlock block = list.get(list.size() - 1 - index);

			ArrayList<String> lore = new ArrayList<>();
			lore.add("Block Type: §c" + block.getMaterial());
			lore.add("Mined at: §c" + String.format("x:%d y:%d z:%d world:%s", block.getX(), block.getY(), block.getZ(), block.getWorld()));
			lore.add("Time mined: §c" + DATE_FORMAT.format(new Date(block.getTimestamp())));
			lore.add("Mined by: " + Main.getColorCode() + block.getName());
			lore.add(" ");
			lore.add("§cClick to teleport!");

			linkItemTo(i, new ItemBuilder().displayname(block.getName()).itemstack(new ItemStack(Material.matchMaterial(block.getMaterial()))).lore(lore).build(), new Runnable() {

				@Override
				public void run() {
					BukkitUtils.saveTeleport(opener, new Location(Bukkit.getWorld(block.getWorld()), block.getX(), block.getY(), block.getZ()));
				}
			});

			index++;
		}

		return calculatePages(list.size(), getSize()) == page;
	}
}
