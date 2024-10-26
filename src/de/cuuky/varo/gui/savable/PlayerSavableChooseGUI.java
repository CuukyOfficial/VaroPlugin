package de.cuuky.varo.gui.savable;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.cryptomorin.xseries.XMaterial;

import de.cuuky.varo.Main;
import de.cuuky.varo.gui.VaroListInventory;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.player.stats.stat.inventory.VaroSaveable;
import de.cuuky.varo.player.stats.stat.inventory.VaroSaveable.SaveableType;
import de.varoplugin.cfw.inventory.ItemClick;
import de.varoplugin.cfw.item.ItemBuilder;
import de.varoplugin.cfw.location.SimpleLocationFormat;

public class PlayerSavableChooseGUI extends VaroListInventory<VaroSaveable> {

    public PlayerSavableChooseGUI(Player opener, VaroPlayer target) {
        super(Main.getInventoryManager(), opener, target.getStats().getSaveables());
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
        return ItemBuilder.material(savable.getType() == SaveableType.CHEST ? XMaterial.CHEST : XMaterial.FURNACE).displayName(Main.getColorCode() + savable.getId())
                .lore("§7Location§8: " + new SimpleLocationFormat(Main.getColorCode() + "x§7, " + Main.getColorCode() + "y§7, " + Main.getColorCode() + "z§7 in " + Main.getColorCode() + "world").format(savable.getBlock().getLocation()))
                .build();
    }

    @Override
    protected ItemClick getClick(VaroSaveable varoSaveable) {
        return (event) -> this.openNext(new PlayerSavableGUI(getPlayer(), varoSaveable));
    }
}