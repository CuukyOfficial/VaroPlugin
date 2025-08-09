package de.varoplugin.varo.gui.savable;

import org.bukkit.entity.Player;

import com.cryptomorin.xseries.XMaterial;

import de.varoplugin.cfw.item.ItemBuilder;
import de.varoplugin.varo.Main;
import de.varoplugin.varo.gui.VaroInventory;
import de.varoplugin.varo.player.stats.stat.inventory.VaroSaveable;

public class PlayerSavableGUI extends VaroInventory {

    private final VaroSaveable savable;

    public PlayerSavableGUI(Player player, VaroSaveable savable) {
        super(Main.getInventoryManager(), player);

        this.savable = savable;
    }

    @Override
    public String getTitle() {
        return "§7Savable §e" + savable.getId();
    }

    @Override
    public int getSize() {
        return 36;
    }

    @Override
    public void refreshContent() {
        if (getPlayer().hasPermission("varo.setup"))
            addItem(this.getUsableSize(), ItemBuilder.material(XMaterial.ENDER_PEARL).displayName("§bTeleport").build(),
                    (event) -> getPlayer().teleport(savable.getBlock().getLocation()));

        addItem(this.getCenter(), ItemBuilder.material(XMaterial.RED_DYE).displayName("§cDelete").build(), (event) -> {
            savable.remove();
            this.back();
        });
    }
}