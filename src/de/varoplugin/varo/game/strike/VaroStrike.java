package de.varoplugin.varo.game.strike;

import de.varoplugin.varo.game.VaroGameObject;

public interface VaroStrike extends VaroGameObject {

    VaroStrikable getTarget();

    VaroStrikeType getType();

    boolean wasExecuted();

}