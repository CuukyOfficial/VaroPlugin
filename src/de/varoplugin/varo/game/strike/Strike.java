package de.varoplugin.varo.game.strike;

import de.varoplugin.varo.game.VaroObject;
import de.varoplugin.varo.game.entity.player.Player;

public interface Strike extends VaroObject {

    Player getTarget();

    StrikeType getType();

    void execute();

    boolean wasExecuted();

}