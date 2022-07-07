package de.varoplugin.varo.game.strike;

import org.bukkit.Location;

public interface VaroStrikable {

    boolean strike(VaroStrike strike);

    void kill();

    boolean isOnline();

    boolean clearInventory();

    Location getLocation();

}