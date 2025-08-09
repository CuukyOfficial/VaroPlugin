package de.varoplugin.varo.gui.youtube;

import org.bukkit.entity.Player;

import com.cryptomorin.xseries.XMaterial;

import de.varoplugin.cfw.item.ItemBuilder;
import de.varoplugin.varo.Main;
import de.varoplugin.varo.gui.VaroInventory;
import de.varoplugin.varo.player.stats.stat.YouTubeVideo;

public class YouTubeVideoOptionsGUI extends VaroInventory {

    private YouTubeVideo video;

    public YouTubeVideoOptionsGUI(Player player, YouTubeVideo video) {
        super(Main.getInventoryManager(), player);

        this.video = video;
    }

    @Override
    public String getTitle() {
        return "§5" + video.getVideoId();
    }

    @Override
    public int getSize() {
        return 36;
    }

    @Override
    public void refreshContent() {
        addItem(11, ItemBuilder.material(XMaterial.PAPER).displayName("§aOpen").build(), (event) -> {
            getPlayer().sendMessage(Main.getPrefix() + "Link:");
            getPlayer().sendMessage(Main.getPrefix() + video.getLink());
        });

        addItem(15, ItemBuilder.material(XMaterial.REDSTONE).displayName("§cRemove").build(), (event) -> {
            video.remove();
            this.back();
        });
    }
}