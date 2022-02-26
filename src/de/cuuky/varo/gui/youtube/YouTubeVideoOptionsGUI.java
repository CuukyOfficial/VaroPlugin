package de.cuuky.varo.gui.youtube;

import de.cuuky.cfw.utils.item.BuildItem;
import de.cuuky.varo.app.Main;
import de.cuuky.varo.entity.player.stats.stat.YouTubeVideo;
import de.cuuky.varo.gui.VaroInventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class YouTubeVideoOptionsGUI extends VaroInventory {

    private YouTubeVideo video;

    public YouTubeVideoOptionsGUI(Player player, YouTubeVideo video) {
        super(Main.getCuukyFrameWork().getAdvancedInventoryManager(), player);

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
        addItem(11, new BuildItem().displayName("§aOpen").itemstack(new ItemStack(Material.PAPER)).build(), (event) -> {
            getPlayer().sendMessage(Main.getPrefix() + "Link:");
            getPlayer().sendMessage(Main.getPrefix() + video.getLink());
        });

        addItem(15, new BuildItem().displayName("§cRemove").itemstack(new ItemStack(Material.REDSTONE)).build(), (event) -> {
            video.remove();
            this.back();
        });
    }
}