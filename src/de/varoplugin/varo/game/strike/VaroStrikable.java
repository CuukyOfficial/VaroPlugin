package de.varoplugin.varo.game.strike;

import de.varoplugin.varo.game.VaroGameObject;
import org.bukkit.Location;

public interface VaroStrikable extends VaroGameObject {

    boolean strike(VaroStrike strike);

    void kill();

    boolean isOnline();

    boolean clearInventory();

    Location getLocation();

    String getName();

}