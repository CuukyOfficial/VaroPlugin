package de.cuuky.varo.gui.youtube;

import de.cuuky.cfw.inventory.ItemClick;
import de.cuuky.cfw.item.ItemBuilder;
import de.cuuky.varo.Main;
import de.cuuky.varo.entity.player.stats.stat.YouTubeVideo;
import de.cuuky.varo.gui.VaroListInventory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;

public class YouTubeVideoListGUI extends VaroListInventory<YouTubeVideo> {

    public YouTubeVideoListGUI(Player player) {
        super(Main.getCuukyFrameWork().getAdvancedInventoryManager(), player, YouTubeVideo.getVideos());
    }

    @Override
    public String getTitle() {
        return "§5Videos";
    }

    @Override
    public int getSize() {
        return 54;
    }

    @Override
    protected ItemStack getItemStack(YouTubeVideo video) {
        return new ItemBuilder().displayname("§5" + video.getTitle())
                .lore(new String[]{"§7Detected at: " + new SimpleDateFormat("dd.MMM.yyyy HH:mm")
                        .format(video.getDetectedAt()), "§7User: " +
                        (video.getOwner() != null ? video.getOwner().getName() : "/"), "§7" + video.getDuration(), "§7Link: " + video.getLink()})
                .playername(video.getOwner() != null ? video.getOwner().getName() : "UNKNOWN").build();
    }

    @Override
    protected ItemClick getClick(YouTubeVideo video) {
        return (event) -> {
            if (!getPlayer().hasPermission("varo.player")) {
                getPlayer().sendMessage("§7Video: " + video.getLink());
                return;
            }

            this.openNext(new YouTubeVideoOptionsGUI(getPlayer(), video));
        };
    }
}
