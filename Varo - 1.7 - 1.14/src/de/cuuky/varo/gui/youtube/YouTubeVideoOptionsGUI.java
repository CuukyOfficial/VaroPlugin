package de.cuuky.varo.gui.youtube;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.Main;
import de.cuuky.varo.gui.SuperInventory;
import de.cuuky.varo.gui.utils.PageAction;
import de.cuuky.varo.item.ItemBuilder;
import de.cuuky.varo.player.stats.stat.YouTubeVideo;

public class YouTubeVideoOptionsGUI extends SuperInventory {

	private YouTubeVideo video;

	public YouTubeVideoOptionsGUI(Player opener, YouTubeVideo video) {
		super("§5" + video.getVideoId(), opener, 9, false);

		this.video = video;

		open();
	}

	@Override
	public boolean onOpen() {
		linkItemTo(1, new ItemBuilder().displayname("§aOpen").itemstack(new ItemStack(Material.PAPER)).build(), new Runnable() {

			@Override
			public void run() {
				opener.sendMessage(Main.getPrefix() + "Link:");
				opener.sendMessage(Main.getPrefix() + video.getLink());
			}
		});

		linkItemTo(8, new ItemBuilder().displayname("§cRemove").itemstack(new ItemStack(Material.REDSTONE)).build(), new Runnable() {

			@Override
			public void run() {
				video.remove();
				new YouTubeVideoListGUI(opener);
			}
		});

		return true;
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
