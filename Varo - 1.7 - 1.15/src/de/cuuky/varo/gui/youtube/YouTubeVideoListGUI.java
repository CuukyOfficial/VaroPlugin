package de.cuuky.varo.gui.youtube;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import de.cuuky.varo.entity.player.stats.stat.YouTubeVideo;
import de.cuuky.varo.gui.SuperInventory;
import de.cuuky.varo.gui.utils.PageAction;
import de.cuuky.varo.item.ItemBuilder;

public class YouTubeVideoListGUI extends SuperInventory {

	public YouTubeVideoListGUI(Player opener) {
		super("§5Videos", opener, 45, false);

		open();
	}

	@Override
	public boolean onBackClick() {
		return false;
	}

	@Override
	public void onClick(InventoryClickEvent event) {}

	@Override
	public void onClose(InventoryCloseEvent event) {}

	@Override
	public void onInventoryAction(PageAction action) {}

	@Override
	public boolean onOpen() {
		ArrayList<YouTubeVideo> list = YouTubeVideo.getVideos();

		int start = getSize() * (getPage() - 1);
		for(int i = 0; i != getSize(); i++) {
			YouTubeVideo video;
			try {
				video = list.get(start);
			} catch(IndexOutOfBoundsException e) {
				break;
			}

			;

			linkItemTo(i, new ItemBuilder().displayname("§5" + video.getTitle()).lore(new String[] { "§7Detected at: " + new SimpleDateFormat("dd.MMM.yyyy HH:mm").format(video.getDetectedAt()), "§7User: " + (video.getOwner() != null ? video.getOwner().getName() : "/"), "§7" + video.getDuration(), "§7Link: " + video.getLink() }).playername(video.getOwner() != null ? video.getOwner().getName() : "UNKNOWN").build(), new Runnable() {

				@Override
				public void run() {
					if(!opener.hasPermission("varo.player")) {
						opener.sendMessage("§7Video: " + video.getLink());
						return;
					}

					new YouTubeVideoOptionsGUI(opener, video);
				}
			});
			start++;
		}
		return calculatePages(YouTubeVideo.getVideos().size(), getSize()) == getPage();
	}
}
