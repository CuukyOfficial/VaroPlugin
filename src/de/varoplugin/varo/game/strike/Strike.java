package de.varoplugin.varo.game.strike;

import de.varoplugin.varo.game.VaroGameObject;
import de.varoplugin.varo.game.entity.player.VaroPlayer;

public interface Strike extends VaroGameObject {

    VaroPlayer getTarget();

    StrikeType getType();

    void execute();

    boolean wasExecuted();

}