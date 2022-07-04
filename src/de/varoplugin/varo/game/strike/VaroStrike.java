package de.varoplugin.varo.game.strike;

import de.varoplugin.varo.game.VaroGameObject;
import de.varoplugin.varo.game.entity.player.VaroPlayer;

public interface VaroStrike extends VaroGameObject {

    VaroPlayer getTarget();

    VaroStrikeType getType();

    void execute();

    boolean wasExecuted();

}