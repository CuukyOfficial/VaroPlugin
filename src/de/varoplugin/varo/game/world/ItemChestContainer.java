package de.varoplugin.varo.game.world;

import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.Collection;
import java.util.stream.Stream;

public interface ItemChestContainer {

    void addItemChests(Collection<Block> location);

    Stream<Location> getItemChestLocations();

}
