package de.cuuky.varo.gui.saveable;

import de.cuuky.cfw.inventory.ItemClick;
import de.cuuky.cfw.item.ItemBuilder;
import de.cuuky.cfw.utils.LocationFormat;
import de.cuuky.varo.Main;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.stats.stat.inventory.VaroSaveable;
import de.cuuky.varo.entity.player.stats.stat.inventory.VaroSaveable.SaveableType;
import de.cuuky.varo.gui.VaroListInventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerSavableChooseGUI extends VaroListInventory<VaroSaveable> {

    public PlayerSavableChooseGUI(Player opener, VaroPlayer target) {
        super(Main.getCuukyFrameWork().getAdvancedInventoryManager(), opener, target.getStats().getSaveables());
    }

    @Override
    public String getTitle() {
        return "§eÖfen/Kisten";
    }

    @Override
    public int getSize() {
        return this.getRecommendedSize();
    }

    @Override
    protected ItemStack getItemStack(VaroSaveable savable) {
        return new ItemBuilder().displayname(Main.getColorCode() + savable.getId())
                .itemstack(new ItemStack(savable.getType() == SaveableType.CHEST ? Material.CHEST : Material.FURNACE))
                .lore("§7Location§8: " + new LocationFormat(savable.getBlock().getLocation())
                        .format(Main.getColorCode() + "x§7, " + Main.getColorCode() + "y§7, " + Main.getColorCode() + "z§7 in " + Main.getColorCode() + "world"))
                .build();
    }

    @Override
    protected ItemClick getClick(VaroSaveable varoSaveable) {
        return (event) -> this.openNext(new PlayerSavableGUI(getPlayer(), varoSaveable));
    }
}