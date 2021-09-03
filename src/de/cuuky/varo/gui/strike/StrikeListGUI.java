package de.cuuky.varo.gui.strike;

import de.cuuky.cfw.inventory.ItemClick;
import de.cuuky.cfw.utils.item.BuildItem;
import de.cuuky.varo.Main;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.stats.stat.Strike;
import de.cuuky.varo.gui.VaroListInventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;

public class StrikeListGUI extends VaroListInventory<Strike> {

    public StrikeListGUI(Player player, Player target) {
        super(Main.getCuukyFrameWork().getAdvancedInventoryManager(), player, VaroPlayer.getPlayer(target).getStats().getStrikes());
    }

    @Override
    public String getTitle() {
        return "Strikes";
    }

    @Override
    public int getSize() {
        return this.getRecommendedSize();
    }

    @Override
    protected ItemStack getItemStack(Strike strike) {
        return new BuildItem().displayName("§c" + strike.getStrikeNumber())
                .itemstack(new ItemStack(Material.PAPER)).lore(new String[]{"§7Reason: §c" + strike.getReason(), "§7Striker: §c" + strike.getStriker(),
                        "§7Date: §c" + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
                                .format(strike.getAcquiredDate())}).build();
    }

    @Override
    protected ItemClick getClick(Strike strike) {
        return null;
    }
}