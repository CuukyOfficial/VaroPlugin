package de.varoplugin.varo.game.strike;

import de.varoplugin.varo.game.entity.VaroEntity;
import org.bukkit.Location;

public interface VaroStrikable extends VaroEntity {

    boolean strike(VaroStrike strike);

    void kill();

    boolean isOnline();

    boolean clearInventory();

    Location getLocation();

}