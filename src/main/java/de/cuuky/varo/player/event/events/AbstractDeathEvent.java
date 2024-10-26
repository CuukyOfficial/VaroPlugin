package de.cuuky.varo.player.event.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.player.event.BukkitEvent;
import de.cuuky.varo.player.event.BukkitEventType;
import de.cuuky.varo.player.stats.VaroInventory;

public abstract class AbstractDeathEvent extends BukkitEvent {

    protected AbstractDeathEvent(BukkitEventType eventType) {
        super(eventType);
    }

    protected void dropInventory(ItemStack[] items, Location location) {
        for (ItemStack item : items)
            if (item != null && item.getType() != Material.AIR)
                location.getWorld().dropItemNaturally(location, item);
    }

    protected void dropInventory(VaroInventory inventory, Location location) {
        this.dropInventory(inventory.getInventory().getContents(), location);
    }
}