package de.varoplugin.varo.game.strike;

import org.bukkit.Location;

public interface Strikable {

    boolean strike(Strike strike);

    void kill();

    boolean isOnline();

    boolean clearInventory();

    Location getLocation();

}