package de.varoplugin.varo.gui.youtube;

import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.cryptomorin.xseries.XMaterial;

import de.varoplugin.cfw.inventory.ItemClick;
import de.varoplugin.cfw.item.ItemBuilder;
import de.varoplugin.varo.Main;
import de.varoplugin.varo.gui.VaroListInventory;
import de.varoplugin.varo.player.VaroPlayer;
import de.varoplugin.varo.player.stats.stat.YouTubeVideo;

public class YouTubeVideoListGUI extends VaroListInventory<YouTubeVideo> {

    public YouTubeVideoListGUI(Player player) {
        super(Main.getInventoryManager(), player, YouTubeVideo.getVideos());
    }

    public YouTubeVideoListGUI(Player player, VaroPlayer target) {
        super(Main.getInventoryManager(), player, YouTubeVideo.getVideos().stream().filter(video -> video.getOwner() == target).collect(Collectors.toList()));
    }

    @Override
    public String getTitle() {
        return "§5Videos";
    }

    @Override
    public int getSize() {
        return this.getRecommendedSize();
    }

    @Override
    protected ItemStack getItemStack(YouTubeVideo video) {
        ItemBuilder itemBuilder = video.getOwner() != null ? ItemBuilder.skull(video.getOwner().getRealUUID()) : ItemBuilder.material(XMaterial.SKELETON_SKULL);
        return itemBuilder.displayName("§5" + video.getTitle())
                .lore(new String[]{"§7Detected at: " + new SimpleDateFormat("dd.MMM.yyyy HH:mm")
                        .format(video.getDetectedAt()), "§7User: " + (video.getOwner() != null ?
                        video.getOwner().getName() : "/"), "§7Link: " + video.getLink()}).build();
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
