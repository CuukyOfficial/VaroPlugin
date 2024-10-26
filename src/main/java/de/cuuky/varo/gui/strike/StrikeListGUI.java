package de.cuuky.varo.gui.strike;

import java.text.SimpleDateFormat;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.cryptomorin.xseries.XMaterial;

import de.cuuky.varo.Main;
import de.cuuky.varo.gui.VaroListInventory;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.player.stats.stat.Strike;
import de.varoplugin.cfw.inventory.ItemClick;
import de.varoplugin.cfw.item.ItemBuilder;

public class StrikeListGUI extends VaroListInventory<Strike> {

    public StrikeListGUI(Player player, VaroPlayer target) {
        super(Main.getInventoryManager(), player, target.getStats().getStrikes());
    }

    public StrikeListGUI(Player player, Player target) {
        this(player, VaroPlayer.getPlayer(target));
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
        return ItemBuilder.material(XMaterial.PAPER).displayName("§c" + strike.getStrikeNumber())
                .lore(new String[]{"§7Reason: §c" + strike.getReason(), "§7Striker: §c" + strike.getStriker(),
                        "§7Date: §c" + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(strike.getAcquiredDate())}).build();
    }

    @Override
    protected ItemClick getClick(Strike strike) {
        return null;
    }
}