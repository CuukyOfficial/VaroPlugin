package de.varoplugin.varo.task;

import de.varoplugin.varo.game.Varo;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class FillChestsTask extends VaroRunnableTask {

    public FillChestsTask(Varo varo) {
        super(varo);
    }

    private void fillChest(Chest chest) {
        chest.getBlockInventory().addItem(new ItemStack(Material.APPLE));
    }

    @Override
    protected void onEnable() {
        // TODO: Add event
        this.getVaro().getItemChestLocations().map(Location::getBlock)
                .filter(Objects::nonNull).filter(b -> b.getType().equals(Material.CHEST))
                .map(b -> (Chest) b.getState()).forEach(this::fillChest);
    }

    @Override
    protected void onDisable() {
    }
}
